package com.ase.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UserCalendar {

    private Calendar calender;

    private List<Date> registeredEvents;
    private List<Date> bookmarkedEvents;

    public UserCalendar(List<Date> registeredEvents,
            List<Date> bookmarkedEvents) {
        this.calender = Calendar.getInstance();
        this.registeredEvents = registeredEvents;
        this.bookmarkedEvents = bookmarkedEvents;
    }

    public List<Date> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(List<Date> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public List<Date> getBookmarkedEvents() {
        return bookmarkedEvents;
    }

    public void setBookmarkedEvents(List<Date> bookmarkedEvents) {
        this.bookmarkedEvents = bookmarkedEvents;
    }

    public Date getCalenderTime() {
        return calender.getTime();
    }

}
