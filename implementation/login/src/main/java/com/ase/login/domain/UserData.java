package com.ase.login.domain;

/**
 * Responsible for holding user data. The difference to {@link MyUser} is, that this class not
 * contain a userId and that it is not persisted.
 */
public class UserData implements IUserData {

    private final String email;
    private final String password;
    private final ERole role;

    public UserData(String email, String password, ERole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public ERole getRole() {
        return role;
    }
}
