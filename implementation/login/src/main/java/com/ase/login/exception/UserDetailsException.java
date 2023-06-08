package com.ase.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The provided user details are not found")
public class UserDetailsException extends Exception {

    public UserDetailsException(String message) {
        super(message);
    }

    public UserDetailsException() {
        super();
    }
}
