package com.ase.calendar;


public abstract class ACalendarExportType {

    public UserCalendar calender;

    public ACalendarExportType(UserCalendar calender) {
        this.calender = calender;
    }

    public abstract String convert();
}
