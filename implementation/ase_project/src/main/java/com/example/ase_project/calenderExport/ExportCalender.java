package com.example.ase_project.calenderExport;

import com.example.ase_project.calenderExport.calenderFactory.ACalenderExportFactory;
import com.example.ase_project.calenderExport.calenderFactory.ConcreteCalenderExportFactoryICal;
import com.example.ase_project.calenderExport.calenderFactory.ConcreteCalenderExportFactoryJSON;
import com.example.ase_project.calenderExport.calenderFactory.ConcreteCalenderExportFactoryXML;

import java.util.Calendar;

public class ExportCalender {

    private ACalenderExportFactory exportFactory;
    private ACalenderExportType calenderType;
    private EExportType exportType;
    private UserCalender userCalender;

    public ExportCalender(UserCalender calender, EExportType type) {
        this.userCalender = calender;
        this.exportType = type;
    }

    public ExportCalender(EExportType type){
        this.exportType = type;
    }
    public ACalenderExportType createExportCalender(EExportType exportType, UserCalender userCalenderExport) throws Exception {
        switch (this.exportType) {
            case XML -> exportFactory = new ConcreteCalenderExportFactoryXML();
            case JSON -> exportFactory = new ConcreteCalenderExportFactoryJSON();
            case ICal -> exportFactory = new ConcreteCalenderExportFactoryICal();
            default -> {
                return null;
            }
        }
        return exportFactory.createExportCalender(userCalenderExport);
    }


}
