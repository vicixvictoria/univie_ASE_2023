package com.ase.calendar.calendarFactory;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.ase.calendar.calendarExportTypes.CalendarXMLExport;

public class ConcreteCalendarExportFactoryXML extends ACalendarExportFactory {

    @Override
    public ACalendarExportType createExportCalender(UserCalendar calender) {
        CalendarXMLExport calendarXMLExport = new CalendarXMLExport(calender);
        return calendarXMLExport;
    }
}