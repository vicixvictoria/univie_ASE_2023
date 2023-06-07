package com.ase.attendance.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.ArrayList;

/**
 * The Attendees class represents the attendees of an event.
 */
@Entity
public class Attendees {

    @Id
    private String eventID;
    private int capacity;
    @ElementCollection
    @CollectionTable(name="listOfAttendeeIDs")
    private ArrayList <String> attendees;

    /**
     * Default constructor for the Attendees class.
     */
    public Attendees() {
    }

    /**
     * Constructs an Attendees object with the specified event ID and capacity.
     *
     * @param eventID   The ID of the event.
     * @param capacity  The capacity of the event.
     */
    public Attendees(String eventID, int capacity) {
        this.eventID = eventID;
        this.capacity = capacity;
        attendees = new ArrayList<String>();
    }

    /**
     * Returns the ID of the event.
     *
     * @return The event ID.
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Adds an attendee to the list of attendees.
     *
     * @param attendeeID The ID of the attendee to add.
     */
    public void addAttendee(String attendeeID) {

        if(attendees.size() < capacity) {
            attendees.add(attendeeID);
        }
    }

    /**
     * Removes an attendee from the list of attendees.
     *
     * @param attendeeID The ID of the attendee to remove.
     */
    public void removeAttendee(String attendeeID) {
        attendees.remove(attendeeID);
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

    /**
     * Returns the list of attendees.
     *
     * @return The list of attendee IDs.
     */
    public ArrayList<String> getAttendees() {
        return attendees;
    }
}
