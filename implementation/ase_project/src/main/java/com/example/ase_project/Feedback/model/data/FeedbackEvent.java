package com.example.ase_project.Feedback.model.data;

public class FeedbackEvent {
    private final String eventID;

    public FeedbackEvent(String eventID) {
        if (eventID == null) { throw new IllegalArgumentException("eventID can not be null"); }
        this.eventID = eventID;
    }

    public String getEventID() {
        return this.eventID;
    }
}
