package com.ase.sendNotification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NotificationUser {

    @Id
    private String id;
    private String email;

    public NotificationUser() {
    }

    public NotificationUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
