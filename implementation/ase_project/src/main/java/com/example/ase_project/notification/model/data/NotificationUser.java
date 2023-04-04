package com.example.ase_project.notification.model.data;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;

import java.util.Objects;

//@Entity
public class NotificationUser{

    //@Id
    //@Nonnull
    private String id; // TODO: Potentially change this. Depends a little how it will be implemented in the regular user
    //@Nonnull
    private String email;

    // JPA Entities need to have an empty constructor
    public NotificationUser() {}

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
        if (this == o) return true;
        if (!(o instanceof NotificationUser that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
