package com.example.ase_project.calenderExport;


public abstract class ACalenderExportType {

    public UserCalender calender;

    public ACalenderExportType(UserCalender calender) {
        this.calender = calender;
    }

    public abstract String convert();
}
