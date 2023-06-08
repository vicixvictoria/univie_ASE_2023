package com.ase.calendar.calendarExportTypes;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.CalendarEvent;
import com.ase.calendar.UserCalendar;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalendarXMLExport extends ACalendarExportType {

    public CalendarXMLExport(UserCalendar calender) {
        super(calender);
    }

    /**
     * converts Calendar into XML calendar in String format
     *
     * @return String representing an XML Calendar
     */
    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        String xml = "";
        String xmlHeader = "<?xml version=\"1.0\"?>\n"
                + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"+"<root>\n";
        StringBuilder eventsXML = new StringBuilder();
        eventsXML.append("<registeredEvents>\n");
        for (CalendarEvent registeredEvent : this.calender.getRegisteredEvents()) {
            eventsXML.append(String.format("<eventName>%s</eventName>\n<eventDate>%s</eventDate>\n<description>%s</description>\n",registeredEvent.getEventName(),registeredEvent.getEventDate(),registeredEvent.getDescription()));
        }
        eventsXML.append("</registeredEvents>\n<bookmarkedEvents>\n");

        for (CalendarEvent bookmarkedEvent : this.calender.getBookmarkedEvents()) {
            eventsXML.append(String.format("<eventName>%s</eventName>\n<eventDate>%s</eventDate>\n<description>%s</description>\n",bookmarkedEvent.getEventName(),bookmarkedEvent.getEventDate(),bookmarkedEvent.getDescription()));
        }

        String xmlFooter = "</bookmarkedEvents>\n</root>\n</xs:schema>\n";
        return xmlHeader+eventsXML+ xmlFooter;

    }
}
