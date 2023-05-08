package com.ase.notifaction.model.data;

import java.util.Objects;

/**
 * Used to store relevant information for the notification domain about a user of the application
 */
public class NotificationUser {

    private String id;
    private String email;

    public NotificationUser() {
    }

    public NotificationUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationUser that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
