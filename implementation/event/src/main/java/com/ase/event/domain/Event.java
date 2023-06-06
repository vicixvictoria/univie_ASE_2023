package com.ase.event.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Event {

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
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
        this.eventID = UUID.randomUUID().toString();
        this.type = type;
        this.capacity = validateCapacity(capacity);
        this.date = date;
        this.description = validateDescription(description);
        this.organizerID = validateOrganizerID(organizerID);
        this.eventName = validateName(eventName);
    }

    public Event(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        this.eventID = UUID.randomUUID().toString();
        this.type = event.getType();
        this.capacity = validateCapacity(event.getCapacity());
        this.date = event.getDate();
        this.description = validateDescription(event.getDescription());
        this.organizerID = validateOrganizerID(event.getOrganizerID());
        this.eventName = validateName(event.getEventName());
    }

    private static String validateOrganizerID(String id) {
        if (id == null) {
            throw new IllegalArgumentException("OrganizerID can not be null");
        }
        return id;
    }

    private static String validateDescription(String description) {
        if (description == null) {
            description = "";
        }
        return description;
    }

    private static String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Event Name can not be null");
        }
        return name;
    }

    private static int validateCapacity(int capacity) {
        if (capacity>0 ) {
            return capacity;
        }
        throw new IllegalArgumentException("Capacity can not be zero");
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

