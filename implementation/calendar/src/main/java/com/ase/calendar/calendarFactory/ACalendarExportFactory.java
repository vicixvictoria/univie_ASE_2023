package com.ase.calendar.calendarFactory;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;

public abstract class ACalendarExportFactory {

    public abstract ACalendarExportType createExportCalender(UserCalendar calender);
}
