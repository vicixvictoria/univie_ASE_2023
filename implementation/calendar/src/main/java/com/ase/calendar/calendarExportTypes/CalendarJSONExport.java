package com.ase.calendar.calendarExportTypes;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalendarJSONExport extends ACalendarExportType {

    public CalendarJSONExport(UserCalendar calender) {
        super(calender);
    }

    /**
     * converts Calendar into JSON calendar in String format
     *
     * @return String representing an JSON Calendar
     */
    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(calender);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
