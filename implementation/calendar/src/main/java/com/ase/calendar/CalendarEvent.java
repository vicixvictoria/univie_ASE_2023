package com.ase.calendar;

import java.util.Date;

public class CalendarEvent {

    private final String eventName;
    private final Date eventDate;
    private final String description;

    public CalendarEvent(String eventName, Date eventDate, String description){
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.description = description;
    }

    public Date getEventDate(){
        return this.eventDate;
    }

    public String getEventName(){
        return this.eventName;
    }

    public String getDescription(){return this.description;}
}
