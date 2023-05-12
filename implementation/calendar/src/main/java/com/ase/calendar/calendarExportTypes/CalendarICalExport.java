package com.ase.calendar.calendarExportTypes;


import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import java.util.Date;

public class CalendarICalExport extends ACalendarExportType {

    public CalendarICalExport(UserCalendar calender) {
        super(calender);
    }

    @Override
    public String convert() {
        String ICalBeginTag = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:Even_App\nMETHOD:PUBLISH\n";

        StringBuilder ICalRegistered = new StringBuilder();
        for (Date registeredEvent : this.calender.getRegisteredEvents()) {
            ICalRegistered.append(String.format(
                    "BEGIN:VEVENT\nUID:%s\nLOCATION:%s\nSUMMARY:%s\nDESCRIPTION:REGISTERED%s\nCLASS:PUBLIC\nDTSTART:%s\nDTEND:%s\nDTSTAMP:%s\nEND:VEVENT\n",
                    "1", "2", "3", "4", "5", "6", "7"));
        }

        StringBuilder ICalBookmarked = new StringBuilder();
        for (Date bookmarkedEvent : this.calender.getBookmarkedEvents()) {
            ICalBookmarked.append(String.format(
                    "BEGIN:VEVENT\nUID:%s\nLOCATION:%s\nSUMMARY:%s\nDESCRIPTION:BOOKMARKED%s\nCLASS:PUBLIC\nDTSTART:%s\nDTEND:%s\nDTSTAMP:%s\nEND:VEVENT\n",
                    "1", "2", "3", "4", "5", "6", "7"));
        }

        String ICalEndTag = "END:VCALENDAR";
        return ICalBeginTag + ICalBookmarked + ICalRegistered + ICalEndTag;
    }

}
