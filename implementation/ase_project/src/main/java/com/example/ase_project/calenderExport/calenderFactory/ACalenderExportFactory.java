package com.example.ase_project.calenderExport.calenderFactory;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.UserCalender;

public abstract class ACalenderExportFactory {
    public abstract ACalenderExportType createExportCalender(UserCalender calender);
}
