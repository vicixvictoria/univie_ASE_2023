package com.ase.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UserCalendar {

    private Calendar calender;

    private List<CalendarEvent> registeredEvents;
    private List<CalendarEvent> bookmarkedEvents;

    public UserCalendar(){

    }

    public UserCalendar(List<CalendarEvent> registeredEvents,
            List<CalendarEvent> bookmarkedEvents) {
        this.calender = Calendar.getInstance();
        this.registeredEvents = registeredEvents;
        this.bookmarkedEvents = bookmarkedEvents;
    }

    public List<CalendarEvent> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(List<CalendarEvent> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public List<CalendarEvent> getBookmarkedEvents() {
        return bookmarkedEvents;
    }

    public void setBookmarkedEvents(List<CalendarEvent> bookmarkedEvents) {
        this.bookmarkedEvents = bookmarkedEvents;
    }

    public Date getCalenderTime() {
        return calender.getTime();
    }

}
