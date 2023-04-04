package com.example.ase_project.calenderExport.calenderFactory;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.calenderExportTypes.CalenderICalExport;
import com.example.ase_project.calenderExport.UserCalender;

public class ConcreteCalenderExportFactoryICal extends ACalenderExportFactory {

    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderICalExport calenderICalExport = new CalenderICalExport(calender);
        return calenderICalExport;
    }
}
