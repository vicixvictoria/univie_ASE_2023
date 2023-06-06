package com.ase.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.ase.login.business.LoginService;
import com.ase.login.dataAccess.JWTHelper;
import com.ase.login.domain.ERole;
import com.ase.login.domain.IUserData;
import com.ase.login.domain.MyUser;
import com.ase.login.exception.PasswordIncorrectException;
import com.ase.login.exception.UserAlreadyExistsException;
import com.ase.login.exception.UserDetailsException;
import com.ase.login.exception.UserNotFoundException;
import com.ase.login.integration.Publisher;
import com.ase.login.dataAccess.IMyUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginServiceTests {

    private IMyUserRepository userRepository;
    private Publisher publisher;
    private JWTHelper jwtHelper;
    private PasswordEncoder passwordEncoder;
    private final String encodedPassword = "encoded-password";

    private LoginService loginService;

    @BeforeEach
    public void setLoginService() {
        userRepository = Mockito.mock(IMyUserRepository.class);
        publisher = Mockito.mock(Publisher.class);
        jwtHelper = Mockito.mock(JWTHelper.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

        loginService = new LoginService(userRepository, publisher, jwtHelper, passwordEncoder);
    }

    private IUserData getMockUserData() {
        IUserData user = Mockito.mock(IUserData.class);
        when(user.getEmail()).thenReturn("some@email.com");
        when(user.getPassword()).thenReturn("securepassword");
        when(user.getRole()).thenReturn(ERole.ATTENDEE);

        return user;
    }

    private void verifyNoPublisherCall() {
        Mockito.verify(publisher, Mockito.times(0)).userDeleted(any());
        Mockito.verify(publisher, Mockito.times(0)).userUpdated(any());
        Mockito.verify(publisher, Mockito.times(0)).newUserCreated(any());
    }

    @Test
    public void LoginService_addUser_validCall() {
        IUserData userData = getMockUserData();

        try {
            loginService.addUser(userData);
        } catch (UserAlreadyExistsException e) {
            Assertions.fail();
        }

        Mockito.verify(userRepository, times(1))
                .save(ArgumentMatchers.argThat(
                        ele -> ele.getEmail().equals(userData.getEmail()) &&
                                ele.getPassword().equals(encodedPassword) &&
                                ele.getRole().equals(userData.getRole())));

        Mockito.verify(publisher, Mockito.times(1))
                .newUserCreated(ArgumentMatchers.argThat(
                        ele -> ele.getEmail().equals(userData.getEmail()) &&
                                ele.getPassword().equals(encodedPassword) &&
                                ele.getRole().equals(userData.getRole())));
    }

    @Test
    public void LoginService_addUser_addUserTwice() {
        IUserData userData = getMockUserData();

        MyUser user = Mockito.mock(MyUser.class);
        when(userRepository.findMyUserByEmail(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(user));

        try {
            loginService.addUser(userData);
        } catch (UserAlreadyExistsException e) {
            Assertions.fail();
        }

        Executable exec = () -> loginService.addUser(userData);

        Assertions.assertThrows(UserAlreadyExistsException.class, exec);
    }

    @Test
    public void LoginService_verifyToken_validCall() {
        String mockToken = "mockToken";
        when(jwtHelper.verifyToken(mockToken)).thenReturn(true);

        boolean result = loginService.verifyToken(mockToken);

        Assertions.assertTrue(result);
        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_verifyToken_invalidToken() {
        String mockToken = "mockToken";
        when(jwtHelper.verifyToken(mockToken)).thenReturn(false);

        boolean result = loginService.verifyToken(mockToken);

        Assertions.assertFalse(result);
        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_updateUser_validCall() {
        String userId = "someId";
        IUserData updateData = getMockUserData();
        String updateDataEmail = updateData.getEmail();

        MyUser dbUser = Mockito.mock(MyUser.class);
        when(dbUser.getEmail()).thenReturn(updateDataEmail);
        when(userRepository.findById(userId)).thenReturn(Optional.of(dbUser));

        try {
            loginService.updateUser(userId, updateData);
        } catch (UserNotFoundException | UserAlreadyExistsException e) {
            Assertions.fail();
        }

        Mockito.verify(dbUser, Mockito.times(1)).setEmail(updateData.getEmail());
        Mockito.verify(dbUser, Mockito.times(1)).setPassword(encodedPassword);

        Mockito.verify(publisher, Mockito.times(1)).userUpdated(dbUser);
    }

    @Test
    public void LoginService_updateUser_userNotInDb_shouldThrow() {
        String userId = "someId";
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        IUserData updateData = getMockUserData();

        Executable exec = () -> loginService.updateUser(userId, updateData);

        Assertions.assertThrows(UserNotFoundException.class, exec);

        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_updateUser_updateToContainedEmail_shouldThrow() {
        String userId = "someId";
        IUserData updateData = getMockUserData();

        MyUser dbUser = Mockito.mock(MyUser.class);
        String oldEmail = "old@email.com";
        when(dbUser.getEmail()).thenReturn(oldEmail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(dbUser));

        MyUser sameEmailDbUser = Mockito.mock(MyUser.class);

        when(userRepository.findMyUserByEmail(updateData.getEmail())).thenReturn(
                Optional.of(sameEmailDbUser));

        Executable exec = () -> loginService.updateUser(userId, updateData);

        Assertions.assertThrows(UserAlreadyExistsException.class, exec);

        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_getUser_validCall() {
        String userId = "someId";
        MyUser dbUser = Mockito.mock(MyUser.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(dbUser));

        try {
            MyUser returnedUser = loginService.getUser(userId);
            Assertions.assertEquals(returnedUser, dbUser);
        } catch (UserNotFoundException e) {
            Assertions.fail();
        }

        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_getUser_userNotInDb_shouldThrow() {
        String userId = "someId";
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Executable exec = () -> loginService.getUser(userId);

        Assertions.assertThrows(UserNotFoundException.class, exec);
    }

    @Test
    public void LoginService_deleteUser_validCall() {
        String userId = "someId";
        MyUser dbUser = Mockito.mock(MyUser.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(dbUser));

        try {
            loginService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            Assertions.fail();
        }

        Mockito.verify(publisher, Mockito.times(1)).userDeleted(dbUser);
        Mockito.verify(userRepository, Mockito.times(1)).delete(dbUser);
    }

    @Test
    public void LoginService_deleteUser_notInDb_shouldThrow() {
        String userId = "someId";
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Executable exec = () -> loginService.deleteUser(userId);

        Assertions.assertThrows(UserNotFoundException.class, exec);

        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_login_validCall() {
        String email = "email@email.com";
        String password = "mypassword";

        MyUser dbUser = Mockito.mock(MyUser.class);
        when(dbUser.getEmail()).thenReturn(email);
        when(dbUser.getPassword()).thenReturn(encodedPassword);

        when(userRepository.findMyUserByEmail(email)).thenReturn(Optional.of(dbUser));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        try {
            loginService.login(email, password);
        } catch (UserDetailsException e) {
            Assertions.fail();
        }

        verifyNoPublisherCall();

        Mockito.verify(jwtHelper, Mockito.times(1)).generateJWTResponse(dbUser);
    }

    @Test
    public void LoginService_login_mismatchedPasswords() {
        String email = "email@email.com";
        String password = "mypassword";

        MyUser dbUser = Mockito.mock(MyUser.class);
        when(dbUser.getEmail()).thenReturn(email);
        when(dbUser.getPassword()).thenReturn("other password");

        when(userRepository.findMyUserByEmail(email)).thenReturn(Optional.of(dbUser));

        Executable exec = () -> loginService.login(email, password);

        Assertions.assertThrows(PasswordIncorrectException.class, exec);
        verifyNoPublisherCall();
    }

    @Test
    public void LoginService_login_notInDb() {
        String email = "email@email.com";
        String password = "mypassword";

        when(userRepository.findMyUserByEmail(email)).thenReturn(Optional.empty());

        Executable exec = () -> loginService.login(email, password);

        Assertions.assertThrows(UserNotFoundException.class, exec);
        verifyNoPublisherCall();
    }
}
