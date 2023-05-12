package com.ase.calendar.calendarExportTypes;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.XML;

public class CalendarXMLExport extends ACalendarExportType {

    public CalendarXMLExport(UserCalendar calender) {
        super(calender);
    }

    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        String xml = "";
        try {
            xml = XML.toString(objectMapper.writeValueAsString(calender));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return xml;
    }
}
