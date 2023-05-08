package com.ase.login.data;

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
