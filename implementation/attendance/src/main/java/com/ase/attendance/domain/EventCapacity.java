package com.ase.attendance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The EventCapacity class represents the capacity of an event.
 */
@Entity
public class EventCapacity {

    @Id
    private String eventID;
    private int capacity;

    /**
     * Default constructor for the EventCapacity class.
     */
    public EventCapacity() {
    }

    /**
     * Constructs an EventCapacity object with the specified event ID and capacity.
     *
     * @param eventID   The ID of the event.
     * @param capacity  The capacity of the event.
     */
    public EventCapacity(String eventID, int capacity) {
        this.eventID = eventID;
    }

    /**
     * Returns the ID of the event.
     *
     * @return The event ID.
     */
    public String getID() {
        return eventID;
    }

    /**
     * Returns the capacity of the event.
     *
     * @return The event capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the event.
     *
     * @param capacity The new capacity to set.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

