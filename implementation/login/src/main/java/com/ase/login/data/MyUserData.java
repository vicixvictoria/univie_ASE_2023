package com.ase.login.data;

public class MyUserData {

    private String email;
    private String password;
    private ERole role;

    public MyUserData(String email, String password, ERole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUserData() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ERole getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}

