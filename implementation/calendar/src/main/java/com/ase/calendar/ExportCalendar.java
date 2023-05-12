package com.ase.calendar;

import com.ase.calendar.calendarFactory.ACalendarExportFactory;
import com.ase.calendar.calendarFactory.ConcreteCalendarExportFactoryICal;
import com.ase.calendar.calendarFactory.ConcreteCalendarExportFactoryJSON;
import com.ase.calendar.calendarFactory.ConcreteCalendarExportFactoryXML;

public class ExportCalendar {

    private ACalendarExportFactory exportFactory;
    private ACalendarExportType calenderType;
    private EExportType exportType;
    private UserCalendar userCalendar;

    public ExportCalendar(UserCalendar calender, EExportType type) {
        this.userCalendar = calender;
        this.exportType = type;
    }

    public ExportCalendar(EExportType type) {
        this.exportType = type;
    }

    public ACalendarExportType createExportCalender(EExportType exportType,
            UserCalendar userCalendarExport) throws Exception {
        switch (this.exportType) {
            case XML -> exportFactory = new ConcreteCalendarExportFactoryXML();
            case JSON -> exportFactory = new ConcreteCalendarExportFactoryJSON();
            case ICal -> exportFactory = new ConcreteCalendarExportFactoryICal();
            default -> {
                return null;
            }
        }
        return exportFactory.createExportCalender(userCalendarExport);
    }


}
