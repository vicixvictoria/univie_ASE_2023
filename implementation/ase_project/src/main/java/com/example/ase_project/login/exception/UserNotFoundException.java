package com.example.ase_project.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: should this stay here? it brings spring concepts into our codebase
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The provided user details are not found")
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super();
    }

}
