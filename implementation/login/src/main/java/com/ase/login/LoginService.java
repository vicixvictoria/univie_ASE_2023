package com.ase.login;

import com.ase.login.data.ERole;
import com.ase.login.data.MyToken;
import com.ase.login.data.MyUser;
import com.ase.login.data.MyUserData;
import com.ase.login.exception.UserNotFoundException;
import com.ase.login.repository.IMyUserRepository;
import com.ase.login.repository.MyUserSavable;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final IMyUserRepository myUserRepository;
    private final Publisher publisher;

    @Autowired
    public LoginService(IMyUserRepository myUserRepository, Publisher publisher) {
        this.myUserRepository = myUserRepository;
        this.publisher = publisher;
    }

    public void addUser(MyUserData newUserData) {
        MyUser newUser = new MyUser(newUserData);
        myUserRepository.save(new MyUserSavable(newUser));
        publisher.newUserCreated(newUser);
    }


    public boolean verifyToken(MyToken token) {
        return true; // as this is just a mockup, always return that the token is valid
    }

    public MyUserData getTestUser() {
        return new MyUserData("email.com", "nice_password", ERole.ATTENDEE);
    }

    public void updateUser(String userId, MyUserData user) throws UserNotFoundException {

        Optional<MyUserSavable> queryResult = myUserRepository.findById(userId);

        if (queryResult.isEmpty()) {
            throw new UserNotFoundException("The provided id did not match any registered user");
        }

        MyUserSavable userSavable = queryResult.get();

        userSavable.setEmail(user.getEmail());
        userSavable.setPassword(user.getPassword());

        publisher.userUpdated(new MyUser(user)); // TODO: this is incorrect!
    }
}
