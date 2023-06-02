package com.ase.login.data;

/**
 * Holds the role a user can be assigned.
 */
public enum ERole {
    ATTENDEE("attendee"), ORGANIZER("organizer"), ADMIN("admin");

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
