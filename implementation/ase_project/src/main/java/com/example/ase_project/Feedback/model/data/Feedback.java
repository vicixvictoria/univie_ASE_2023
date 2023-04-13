package com.example.ase_project.Feedback.model.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity
public class Feedback {
    @Id
    private String eventID;
    @Id
    private String userID;
    private int ratingOverall;
    private int ratingLocation;
    private int ratingFood;
    private String comment;

    public Feedback(String eventID, String userID, int ratingOverall, int ratingFood, int ratingLocation, String comment) {
        this.userID = validateUserID(userID);
        this.eventID = validateEventID(eventID);
        this.ratingOverall = validateRating(ratingOverall);
        this.ratingFood = validateRating(ratingFood);
        this.ratingLocation = validateRating(ratingLocation);
        this.comment = validateComment(comment);
    }

    public Feedback() {

    }

    public String getUserID() {
        return userID;
    }

    public String getEventID() {
        return eventID;
    }

    public String getComment() {
        return comment;
    }

    public int getRatingOverall() {
        return ratingOverall;
    }


    private static int validateRating(int rating) {
        if (0 < rating && rating < 6) return rating;
        throw new IllegalArgumentException("rating can only be a number between 1 and 5");
    }

    private static String validateUserID(String userID) {
        if (userID == null) throw new IllegalArgumentException("userID can not be null");
        return userID;
    }
    private static String validateEventID(String eventID) {
        if (eventID == null) throw new IllegalArgumentException("eventID can not be null");
        return eventID;
    }
    private static String validateComment(String comment) {
        if (comment == null) comment = "";
        return comment;
    }
}
