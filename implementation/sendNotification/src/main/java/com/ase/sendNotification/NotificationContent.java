package com.ase.sendNotification;

// TODO: could be done more abstractly if it takes some sort of user object instead of an email string
public record NotificationContent(String email, String message) {}