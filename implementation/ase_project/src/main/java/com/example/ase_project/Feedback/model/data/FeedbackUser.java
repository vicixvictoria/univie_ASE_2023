package com.example.ase_project.Feedback.model.data;

public class FeedbackUser {
    private final String userID;

    public FeedbackUser(String userID) throws IllegalArgumentException {
        if (userID == null) {
            throw new IllegalArgumentException("userID can not be null");
        }
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
