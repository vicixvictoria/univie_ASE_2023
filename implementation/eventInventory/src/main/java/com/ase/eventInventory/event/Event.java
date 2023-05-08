package com.ase.eventInventory.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Event {

    @Id
    private String eventID;
    private EEventTypes type;
    private int capacity;
    private Date date;
    private String description;

    private String organizerID;

    private String eventName;

    //FYI: Feedback is not stored in each event, we fetch the Feedback from the Feedback Service with the EventID


    public Event() {
    }

    public Event(String eventID, EEventTypes type, int capacity, Date date, String description,
            String organizerID, String eventName) {
        this.eventID = eventID;
        this.type = type;
        this.capacity = capacity;
        this.date = date;
        this.description = description;
        this.organizerID = organizerID;
        this.eventName = eventName;
    }

    public Event(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        this.eventID = event.getEventID();
        this.type = event.getType();
        this.capacity = event.getCapacity();
        this.date = event.getDate();
        this.description = event.getDescription();
        this.organizerID = event.getOrganizerID();
        this.eventName = event.getEventName();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public EEventTypes getType() {
        return type;
    }

    public void setType(EEventTypes type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(String organizerID) {
        this.organizerID = organizerID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

