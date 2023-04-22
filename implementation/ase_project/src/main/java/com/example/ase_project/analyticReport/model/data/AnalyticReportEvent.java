package com.example.ase_project.analyticReport.model.data;

import com.example.ase_project.event.Event;

public record AnalyticReportEvent(String eventID, Event event) {

    public AnalyticReportEvent {
        if (eventID == null) {
            throw new IllegalArgumentException("eventID can not be null");
        }
        if (event == null) {
            throw new IllegalArgumentException("event can not be null");
        }
    }

    public int getNumberOfRegistered() {
        // TODO: implement
        return 0;
    }
    public int getNumberOfAttending() {
        // TODO: implement
        return 0;
    }
    public int getNumberOfBookmarked() {
        // TODO: implement
        return 0;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.eventID);
        ret.append(this.event.toString());
        return ret.toString();
    }
}
