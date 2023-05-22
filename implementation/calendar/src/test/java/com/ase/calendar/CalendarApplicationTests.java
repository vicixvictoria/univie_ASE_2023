package com.ase.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.catalina.User;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class CalendarApplicationTests {

    CalendarController controller;
    UserCalendar calendar;
    @BeforeEach
    void testCreateCalendar() {
        Date registeredEventDate1= new Date(2323223232L);
        Date registeredEventDate2 = new Date(2323223233L);

        List<Date>registeredEvents = new ArrayList<>();
        registeredEvents.add(registeredEventDate1);
        registeredEvents.add(registeredEventDate2);

        Date bookmarkedEventDate1= new Date(2323223232L);
        Date bookmarkedEventDate2 = new Date(2323223233L);

        List<Date>bookmarkedEvents = new ArrayList<>();
        bookmarkedEvents.add(bookmarkedEventDate1);
        bookmarkedEvents.add(bookmarkedEventDate2);

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
    void testCalendarControllerExportJSON() throws Exception {
        this.controller = new CalendarController();
        UserCalendar userCalendarController = controller.setCalender(this.calendar);
        String jsonCalendar = controller.calenderExport(EExportType.JSON,userCalendarController);
        System.out.println(jsonCalendar);
        Assertions.assertNotNull(jsonCalendar);
    }

    @Test
    void testCalendarControllerExportXML() throws Exception {
        this.controller = new CalendarController();
        UserCalendar userCalendarController = controller.setCalender(this.calendar);
        String xmlCalendar = controller.calenderExport(EExportType.XML,userCalendarController);
        System.out.println(xmlCalendar);
        Assertions.assertNotNull(xmlCalendar);
    }

    @Test
    void testCalendarControllerExportICal() throws Exception {
        this.controller = new CalendarController();
        UserCalendar userCalendarController = controller.setCalender(this.calendar);
        String iCalCalendar = controller.calenderExport(EExportType.ICal,userCalendarController);
        System.out.println(iCalCalendar);
        Assertions.assertNotNull(iCalCalendar);
    }


}