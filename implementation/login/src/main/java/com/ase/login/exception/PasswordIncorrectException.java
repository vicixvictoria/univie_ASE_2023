package com.ase.login.exception;

public class PasswordIncorrectException extends UserDetailsException {

    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException() {
        super();
    }

}
