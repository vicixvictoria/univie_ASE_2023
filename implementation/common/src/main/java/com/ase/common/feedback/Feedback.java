package com.ase.common.feedback;

import java.io.Serializable;

public record Feedback(String feedbackID, String eventID, String userID, int ratingOverall, int ratingLocation, int ratingFood, String comment) implements Serializable {
}
