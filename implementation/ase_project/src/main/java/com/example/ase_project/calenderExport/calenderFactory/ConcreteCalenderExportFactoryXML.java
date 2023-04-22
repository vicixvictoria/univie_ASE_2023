package com.example.ase_project.calenderExport.calenderFactory;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.UserCalender;
import com.example.ase_project.calenderExport.calenderExportTypes.CalenderXMLExport;

public class ConcreteCalenderExportFactoryXML extends ACalenderExportFactory {

    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderXMLExport calenderXMLExport = new CalenderXMLExport(calender);
        return calenderXMLExport;
    }
}