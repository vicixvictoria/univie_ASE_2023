package com.example.ase_project.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

class EventServiceTest {


    IEventRepository iEventRepository;
    EventService eventService;
    Date date = new Date();


    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void getAll() {
    }

    @Test
    void getAllEventsByorganizerID() {
    }
/*
    @Test
    void createvalidEvent_whenGetByID_findsOne() {
        Event eventRet1 = eventService.createEvent(new Event(1L, food,20,date,"test",2));
        Event eventRet2 = iEventRepository.findEventByeventID(1L);
        assertEquals(eventRet1,eventRet2);
    }*/
}