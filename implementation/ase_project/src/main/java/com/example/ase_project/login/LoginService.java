package com.example.ase_project.login;

import com.example.ase_project.login.data.ERole;
import com.example.ase_project.login.data.MyToken;
import com.example.ase_project.login.data.MyUser;
import com.example.ase_project.login.data.MyUserData;
import com.example.ase_project.login.exception.UserNotFoundException;
import com.example.ase_project.login.repository.IMyUserRepository;
import com.example.ase_project.login.repository.MyUserSavable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LoginService {

    private final IMyUserRepository myUserRepository;

    @Autowired
    public LoginService(IMyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public void addUser(MyUserData newUserData) {
        MyUser newUser = new MyUser(newUserData);
        myUserRepository.save(new MyUserSavable(newUser));
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
    }
}
