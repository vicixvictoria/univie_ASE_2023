package com.example.ase_project.calenderExport.calenderFactory;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.UserCalender;
import com.example.ase_project.calenderExport.calenderExportTypes.CalenderJSONExport;

public class ConcreteCalenderExportFactoryJSON extends ACalenderExportFactory {

    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderJSONExport calenderJSONExport = new CalenderJSONExport(calender);
        return calenderJSONExport;
    }
}
