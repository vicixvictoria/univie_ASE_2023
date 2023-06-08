package com.ase.calendar.calendarFactory;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.ase.calendar.calendarExportTypes.CalendarICalExport;

public class ConcreteCalendarExportFactoryICal extends ACalendarExportFactory {

    @Override
    public ACalendarExportType createExportCalender(UserCalendar calender) {
        CalendarICalExport calendarICalExport = new CalendarICalExport(calender);
        return calendarICalExport;
    }
}
