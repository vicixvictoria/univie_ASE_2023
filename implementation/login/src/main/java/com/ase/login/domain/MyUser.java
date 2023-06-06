package com.ase.login.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/**
 * The main data class of this Microservice, holding information about a specific user. This class
 * is annotated with JPA annotations and is thu persisted inside a database
 */
@Entity
public class MyUser implements IUserUnmodifiable {

    @Id
    private String id;
    @Column(unique = true)
    private String email;
    private String password;
    private ERole role;

    public MyUser() {
    }

    public MyUser(String id, String email, String password, ERole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public MyUser(String email, String password, ERole role) {
        this(UUID.randomUUID().toString(), email, password, role);
    }

    public MyUser(IUserData userData) {
        this(userData.getEmail(), userData.getPassword(), userData.getRole());
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyUser myUser)) {
            return false;
        }
        return getId().equals(myUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
