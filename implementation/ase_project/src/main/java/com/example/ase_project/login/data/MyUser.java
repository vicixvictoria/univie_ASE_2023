package com.example.ase_project.login.data;

import java.util.UUID;

public class MyUser extends MyUserData {

    private final String id;

    public MyUser(String id, String email, String password, ERole role) {
        super(email, password, role);

        this.id = id;
    }

    public MyUser(String email, String password, ERole role) {
        super(email, password, role);
        this.id = UUID.randomUUID().toString();
    }

    public MyUser(MyUserData data) {
        super(data.getEmail(), data.getPassword(), data.getRole());
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}