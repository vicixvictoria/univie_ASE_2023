package com.ase.login.exception;

public class UserNotFoundException extends UserDetailsException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super();
    }

}
