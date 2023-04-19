package com.example.ase_project.login;

import com.example.ase_project.login.data.MyToken;
import com.example.ase_project.login.data.MyUser;
import com.example.ase_project.login.data.MyUserData;
import com.example.ase_project.login.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * As we need the endpoints location of the login service in multiple laces throughout this service, I have attempted to
 * extract this information into an ENUM `ELoginEndpoints`. This unfortunately did not work, as the endpoints need to
 * be set directly by string constants. What is possible to do, is define constants in the application properties file.
 * I have however decided not to implement these constants for now and simply have the duplicate information in
 * `ELoginService` and this class.
 * TODO: fix this
 * <br>
 * <br>
 * The POST /api/v1/login endpoint, responsible for authenticating a user is not defined in this file but in the
 * JWTAuthenticationFilter.
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody MyUserData newUser) {
        service.addUser(newUser);
    }

    @GetMapping()
    public List<MyUserData> getUsers() {
        return List.of(service.getTestUser());
    }

    @GetMapping(value = "/{role}")
    public List<MyUserData> getUsersByRole(@PathVariable String role) {
        return List.of(service.getTestUser());
    }

    @PutMapping(value = "/{userId}")
    public void updateUsername(@PathVariable String userId, @RequestBody MyUserData user) throws UserNotFoundException {
        service.updateUser(userId, user);
    }

    @PostMapping(value = "/verifyToken")
    public boolean verifyToken(@RequestBody MyToken token) {
        return service.verifyToken(token);
    }
}
