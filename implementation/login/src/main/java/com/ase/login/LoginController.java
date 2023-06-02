package com.ase.login;

import com.ase.common.user.JWTResponse;
import com.ase.common.user.User;
import com.ase.common.user.UserData;
import com.ase.common.user.UserLogin;
import com.ase.login.data.MyUser;
import com.ase.login.exception.UserAlreadyExistsException;
import com.ase.login.exception.UserDetailsException;
import com.ase.login.exception.UserNotFoundException;
import com.ase.login.network.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The controller class, defining REST endpoints to communicate with this microservice.
 * <p>
 * The endpoints either publish plain java classes, or classes defined in the com.ase.common
 * package.
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final Converter converter = new Converter();

    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @ExceptionHandler({UserDetailsException.class, UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> userDetailsInvalid() {
        return new ResponseEntity<>("The requested user was not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/verifyToken")
    public boolean verifyToken(@RequestBody String token) {
        return service.verifyToken(token);
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody UserData newUser) throws UserAlreadyExistsException {
        service.addUser(converter.networkUserDataToInternalUserData(newUser));
    }

    @GetMapping(value = "/{userId}")
    public User get(@PathVariable String userId) throws UserNotFoundException {
        MyUser internal = service.getUser(userId);
        return converter.internalUserToNetworkUser(internal);
    }

    @PutMapping(value = "/{userId}")
    public void update(@PathVariable String userId, @RequestBody UserData userData)
            throws UserNotFoundException, UserAlreadyExistsException {
        service.updateUser(userId, converter.networkUserDataToInternalUserData(userData));
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable String userId) throws UserNotFoundException {
        service.deleteUser(userId);
    }

    @GetMapping(value = "/login")
    public JWTResponse login(@RequestBody UserLogin login) throws UserDetailsException {
        return converter.internalJWTResponseToNetworkJWTResponse(
                service.login(login.email(), login.password()));
    }

    @GetMapping(value = "/healthcheck")
    public ResponseEntity<Void> healthcheck() {
        return ResponseEntity.ok().build();
    }
}
