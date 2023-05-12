package com.ase.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UserCalendar {

    private Calendar calender;

    private List<Date> registeredEvents;   // this then should be list of registered Events, with their dates
    private List<Date> bookmarkedEvents;  // this then should be list of bookmarked Events, with their dates

    public UserCalendar(List<Date> registeredEvents,
            List<Date> bookmarkedEvents) {       //this then should be a Event type, interface to other domains
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
