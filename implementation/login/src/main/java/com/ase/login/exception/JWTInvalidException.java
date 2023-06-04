package com.ase.login.exception;

public class JWTInvalidException extends Exception {

    public JWTInvalidException() {
        super();
    }

    public JWTInvalidException(String message) {
        super(message);
    }
}
