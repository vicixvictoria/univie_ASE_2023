package com.ase.recommender.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.ase.common.event.*;


@Entity
public class EventType {

    @Id
    private String eventID;
    private EEventType eventType;
    public EventType() {
    }

    public EventType(String eventID, EEventType eventType) {
        this.eventID = eventID;
    }

    public String getID() {
        return eventID;
    }

    public EEventType getEventType() {
        return eventType;
    }

    public void setEventType(EEventType eventType) {
        this.eventType = eventType;
    }
}

