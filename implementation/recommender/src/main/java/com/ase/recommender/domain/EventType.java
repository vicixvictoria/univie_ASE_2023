package com.ase.recommender.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.ase.common.event.*;

@Entity
public class EventType {

    @Id
    private String eventID;
    private EEventType eventType;

    /**
     * Default constructor for the EventType class.
     */
    public EventType() {
    }

    /**
     * Constructs a new instance of EventType with the specified event ID and event type.
     *
     * @param eventID    The ID of the event.
     * @param eventType  The type of the event.
     */
    public EventType(String eventID, EEventType eventType) {
        this.eventID = eventID;
    }

    /**
     * Retrieves the ID of the event.
     *
     * @return The ID of the event.
     */
    public String getID() {
        return eventID;
    }

    /**
     * Retrieves the type of the event.
     *
     * @return The type of the event.
     */
    public EEventType getEventType() {
        return eventType;
    }

    /**
     * Sets the type of the event.
     *
     * @param eventType The type of the event.
     */
    public void setEventType(EEventType eventType) {
        this.eventType = eventType;
    }
}

