package com.example.ase_project.Event;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long eventID;
    private EEventTypes type;
    private int capacity;
    private Date date;
    private String description;

    private int organizerID;

    @OneToMany
    private List<Event> feedback; //TODO: change Event to Feedback, Feedback is stored, when Event is created Feedback is empty/null


    public Event() {
    }

    public Event(Long eventID, EEventTypes type, int capacity, Date date, String description, int organizerID, List<Event> feedback) {
        this.eventID = eventID;
        this.type = type;
        this.capacity = capacity;
        this.date = date;
        this.description = description;
        this.organizerID = organizerID;
        this.feedback = feedback;
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
        this.feedback = event.getFeedback();
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
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


    public int getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(int organizerID) {
        this.organizerID = organizerID;
    }

    public List<Event> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Event> feedback) {
        this.feedback = feedback;
    }

}
