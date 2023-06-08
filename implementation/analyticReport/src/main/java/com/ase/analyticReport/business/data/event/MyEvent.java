package com.ase.analyticReport.business.data.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class MyEvent {

    @Id
    private String eventID;
    private EMyEventTypes type;
    private int capacity;
    private Date date;
    private String description;

    private String organizerID;

    private String eventName;

    //FYI: Feedback is not stored in each event, we fetch the Feedback from the Feedback Service with the EventID


    public MyEvent() {
    }

    public MyEvent(String eventID, EMyEventTypes type, int capacity, Date date, String description,
            String organizerID, String eventName) {
        this.eventID = eventID;
        this.type = type;
        this.capacity = capacity;
        this.date = date;
        this.description = description;
        this.organizerID = organizerID;
        this.eventName = eventName;
    }

    public MyEvent(MyEvent myEvent) {
        if (myEvent == null) {
            throw new IllegalArgumentException("Event is null");
        }
        this.eventID = myEvent.getEventID();
        this.type = myEvent.getType();
        this.capacity = myEvent.getCapacity();
        this.date = myEvent.getDate();
        this.description = myEvent.getDescription();
        this.organizerID = myEvent.getOrganizerID();
        this.eventName = myEvent.getEventName();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public EMyEventTypes getType() {
        return type;
    }

    public void setType(EMyEventTypes type) {
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
