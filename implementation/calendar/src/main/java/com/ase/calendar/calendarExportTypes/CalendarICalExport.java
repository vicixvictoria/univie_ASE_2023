package com.ase.calendar.calendarExportTypes;


import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.CalendarEvent;
import com.ase.calendar.UserCalendar;
import java.util.Date;
import java.util.List;

public class CalendarICalExport extends ACalendarExportType {

    public CalendarICalExport(UserCalendar calender) {
        super(calender);
    }

    /**
     * converts Calendar into ICAl calendar in String format
     *
     * @return String representing an ICal Calendar
     */
    @Override
    public String convert() {
        String ICalBeginTag = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:Event_App\nMETHOD:PUBLISH\n";

        StringBuilder ICalRegistered = new StringBuilder();

        for (CalendarEvent registeredEvent : this.calender.getRegisteredEvents()) {
            ICalRegistered.append(String.format(
                    "BEGIN:VEVENT\nSUMMARY:%s\nDESCRIPTION:REGISTERED%s\nCLASS:PUBLIC\nDTSTAMP:%s\nEND:VEVENT\n",
                     registeredEvent.getEventName(), registeredEvent.getDescription(), registeredEvent.getEventDate()));

        }

        StringBuilder ICalBookmarked = new StringBuilder();
        for (CalendarEvent bookmarkedEvent : this.calender.getBookmarkedEvents()) {
            ICalBookmarked.append(String.format(
                    "BEGIN:VEVENT\nSUMMARY:%s\nDESCRIPTION:BOOKMARKED%s\nCLASS:PUBLIC\nDTSTAMP:%s\nEND:VEVENT\n",
                    bookmarkedEvent.getEventName(), bookmarkedEvent.getDescription(), bookmarkedEvent.getEventDate()));
        }

        String ICalEndTag = "END:VCALENDAR";
        return ICalBeginTag + ICalBookmarked + ICalRegistered + ICalEndTag;
    }

}
