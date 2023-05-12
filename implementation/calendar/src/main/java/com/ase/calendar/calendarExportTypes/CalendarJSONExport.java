package com.ase.calendar.calendarExportTypes;

import com.ase.calendar.ACalendarExportType;
import com.ase.calendar.UserCalendar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalendarJSONExport extends ACalendarExportType {

    public CalendarJSONExport(UserCalendar calender) {
        super(calender);
    }

    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(calender);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
