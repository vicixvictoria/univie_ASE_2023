package com.ase.attendance.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class EventCapacity {

    @Id
    private String eventID;
    private int capacity;
    public EventCapacity() {
    }

    public EventCapacity(String eventID, int capacity) {
        this.eventID = eventID;
    }

    public String getID() {
        return eventID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

