package com.example.ase_project.calenderExport.calenderExportTypes;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.UserCalender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalenderJSONExport extends ACalenderExportType {
    public CalenderJSONExport(UserCalender calender) {
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
