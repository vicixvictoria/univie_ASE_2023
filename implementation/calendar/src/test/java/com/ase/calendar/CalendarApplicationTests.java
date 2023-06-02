package com.ase.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalendarApplicationTests {

    CalendarController controller;
    UserCalendar calendar;
    @BeforeEach
    void testCreateCalendar() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date registeredEventDate1= new Date(2323223232L);
        Date registeredEventDate2 = new Date(2323223233L);

        List<CalendarEvent>registeredEvents = new ArrayList<>();
        registeredEvents.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));
        registeredEvents.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));

        Date bookmarkedEventDate1= new Date(2323223232L);
        Date bookmarkedEventDate2 = new Date(2323223233L);

        List<CalendarEvent>bookmarkedEvents = new ArrayList<>();
        bookmarkedEvents.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));
        bookmarkedEvents.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));

        UserCalendar created_calendar= new UserCalendar(registeredEvents, bookmarkedEvents);
        this.calendar = created_calendar;
        Assertions.assertNotNull(created_calendar);
    }

    @Test
    void testCalendar(){
        Assertions.assertNotNull(this.calendar);
    }

    @Test
    void testCalendarControllerSet(){
        this.controller = new CalendarController();
        UserCalendar userCalendarController = controller.setCalender(this.calendar);
        Assertions.assertNotNull(userCalendarController);
    }

    @Test
    void testExporterJSON() throws Exception {
        ExportCalendar exportCalendar = new ExportCalendar(EExportType.JSON);
        List<CalendarEvent>registeredEventsMock = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        registeredEventsMock.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));

        List<CalendarEvent>bookmarkedEventMock = new ArrayList<>();
        bookmarkedEventMock.add(new CalendarEvent("BookmarkTest1", format.parse("17.09.2023 18:00"),"Fun"));


        UserCalendar userCalendar = new UserCalendar(registeredEventsMock, bookmarkedEventMock);
        ACalendarExportType calender = exportCalendar.createExportCalender(EExportType.JSON, userCalendar);
        String JSON = calender.convert();
        Assertions.assertNotNull(JSON);
    }

    @Test
    void testExporterXML() throws Exception {
        ExportCalendar exportCalendar = new ExportCalendar(EExportType.XML);
        List<CalendarEvent>registeredEventsMock = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        registeredEventsMock.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));

        List<CalendarEvent>bookmarkedEventMock = new ArrayList<>();
        bookmarkedEventMock.add(new CalendarEvent("BookmarkTest1", format.parse("17.09.2023 18:00"),"Fun"));


        UserCalendar userCalendar = new UserCalendar(registeredEventsMock, bookmarkedEventMock);
        ACalendarExportType calender = exportCalendar.createExportCalender(EExportType.XML, userCalendar);
        String XML =calender.convert();
        Assertions.assertNotNull(XML);
    }

    @Test
    void testExporterICal() throws Exception {
        ExportCalendar exportCalendar = new ExportCalendar(EExportType.ICal);
        List<CalendarEvent>registeredEventsMock = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        registeredEventsMock.add(new CalendarEvent("TestEvent1", format.parse("16.06.2023 19:00"),"Es ist toll hier"));

        List<CalendarEvent>bookmarkedEventMock = new ArrayList<>();
        bookmarkedEventMock.add(new CalendarEvent("BookmarkTest1", format.parse("17.09.2023 18:00"),"Fun"));


        UserCalendar userCalendar = new UserCalendar(registeredEventsMock, bookmarkedEventMock);
        ACalendarExportType calender = exportCalendar.createExportCalender(EExportType.ICal, userCalendar);
        String ICAL = calender.convert();
        Assertions.assertNotNull(ICAL);
    }

}