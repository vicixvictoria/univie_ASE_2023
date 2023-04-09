package com.example.ase_project.EventInventory;

import com.example.ase_project.Event.Event;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class EventInventory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long eventInventoryID;

    @OneToMany
    private List<Event> eventList;

    private Long organizerID;

    public EventInventory() {
    }

    public EventInventory(List<Event> eventList, Long eventInventoryID, Long organizerID) {
        this.eventList = eventList;
        this.eventInventoryID = eventInventoryID;
        this.organizerID = organizerID;
    }

    public EventInventory(EventInventory eventInventory) {
        this.eventList = eventInventory.getEventList();
        this.eventInventoryID = eventInventory.getEventInventoryID();
        this.organizerID = eventInventory.getOrganizerID();
    }

    public Long getEventInventoryID() {
        return eventInventoryID;
    }

    public void setEventInventoryID(Long eventInventoryID) {
        this.eventInventoryID = eventInventoryID;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Long getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(Long organizerID) {
        this.organizerID = organizerID;
    }

}
