package com.ase.calendar.calendarFactory;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.ase.calendar.calendarExportTypes.CalendarJSONExport;

public class ConcreteCalendarExportFactoryJSON extends ACalendarExportFactory {

    @Override
    public ACalendarExportType createExportCalender(UserCalendar calender) {
        CalendarJSONExport calendarJSONExport = new CalendarJSONExport(calender);
        return calendarJSONExport;
    }
}
