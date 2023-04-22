package com.example.ase_project.login.data;

import java.util.UUID;

public class MyToken {

    private final String token;

    public MyToken(String token) {
        this.token = token;
    }

    public MyToken() {
        token = UUID.randomUUID().toString();
    }

    public String get() {
        return token;
    }
}
