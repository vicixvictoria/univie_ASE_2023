package com.ase.attendance.data;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.ArrayList;

@Entity
public class Attendees {
    @Id
    private String eventID;

    private int capacity;

    @ElementCollection
    @CollectionTable(name="listOfAttendeeIDs")
    private ArrayList <String> attendees;

    public Attendees() {
    }

    public Attendees(String eventID, int capacity) {
        this.eventID = eventID;
        this.capacity = capacity;
        attendees = new ArrayList<String>();
    }

    public void addAttendee(String attendeeID) {

        if(attendees.size() < capacity) {
            attendees.add(attendeeID);
        }
    }

    public void removeAttendee(String attendeeID) {
        attendees.remove(attendeeID);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }
}
