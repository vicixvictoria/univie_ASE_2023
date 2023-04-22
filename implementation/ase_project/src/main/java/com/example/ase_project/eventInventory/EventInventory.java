package com.example.ase_project.eventInventory;

import com.example.ase_project.event.Event;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class EventInventory {

    @Id
    private String eventInventoryID;

    @ElementCollection
    private List<String> eventListID;

    private String organizerID;

    public EventInventory() {
    }

    public EventInventory(List<Event> eventList, String eventInventoryID, List eventListID,
            String organizerID) {
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

    public String getEventInventoryID() {
        return eventInventoryID;
    }

    public void setEventInventoryID(String eventInventoryID) {
        this.eventInventoryID = eventInventoryID;
    }

    public List<String> getEventListID() {
        return eventListID;
    }

    public void setEventListID(List<String> eventListID) {
        this.eventListID = eventListID;
    }

    public String getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(String organizerID) {
        this.organizerID = organizerID;
    }

}
