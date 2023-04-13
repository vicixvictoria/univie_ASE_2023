package com.example.ase_project.EventInventory;

import com.example.ase_project.Event.Event;
import jakarta.persistence.*;

import java.awt.*;
import java.util.List;

@Entity
public class EventInventory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long eventInventoryID;

    @ElementCollection
    private List<Long> eventListID;

    private Long organizerID;

    public EventInventory() {
    }

    public EventInventory(List<Event> eventList, Long eventInventoryID, List eventListID, Long organizerID) {
        this.eventListID = eventListID;
        //this.eventList = eventList;
        this.eventInventoryID = eventInventoryID;
        this.organizerID = organizerID;
    }

    public EventInventory(EventInventory eventInventory) {
        this.eventListID = eventInventory.getEventListID();
        this.eventInventoryID = eventInventory.getEventInventoryID();
        this.organizerID = eventInventory.getOrganizerID();
    }

    public Long getEventInventoryID() {
        return eventInventoryID;
    }

    public void setEventInventoryID(Long eventInventoryID) {
        this.eventInventoryID = eventInventoryID;
    }

    public List<Long> getEventListID() {
        return eventListID;
    }

    public void setEventListID(List<Long> eventListID) {
        this.eventListID = eventListID;
    }

    public Long getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(Long organizerID) {
        this.organizerID = organizerID;
    }

}
