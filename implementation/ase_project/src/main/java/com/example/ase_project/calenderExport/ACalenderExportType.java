package com.example.ase_project.calenderExport;


public abstract class ACalenderExportType {
    public UserCalender calender;
    public abstract String convert();
    public ACalenderExportType(UserCalender calender){
        this.calender = calender;
    }
}
