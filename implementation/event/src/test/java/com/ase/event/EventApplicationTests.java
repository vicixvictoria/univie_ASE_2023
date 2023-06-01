package com.ase.event;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class EventApplicationTests {

    IEventRepository eventRepository;
    EventService eventService;




    @BeforeEach
    public void setEventRepo(){
        Date date1 = new Date();
        Event event1 = new Event("abc", EEventTypes.FOOD, 24, date1, "test1", "xyz", "test1" );
        Event event2 = new Event("abd", EEventTypes.FOOD, 120, date1, "test2", "xyz", "test2" );

        ArrayList<Event> mockResponseAll = new ArrayList<>();
        ArrayList<Event> mockResponseEvent = new ArrayList<>();
        ArrayList<Event> mockResponseUser = new ArrayList<>();

        eventRepository = Mockito.mock(IEventRepository.class);
        when(eventRepository.findAll()).thenReturn(mockResponseAll);
        when(eventRepository.findAllByorganizerID("xyz")).thenReturn(mockResponseUser);

        RabbitTemplate template = Mockito.mock(RabbitTemplate.class);
        Publisher publisher = new Publisher(template);

        eventService = new EventService(eventRepository, publisher);
    }

    /*@Test
    public void eventService_createEvent_fetchEvent_valid() {
        Date date1 = new Date();
        Event event1 = new Event("abc", EEventTypes.FOOD, 24, date1, "test1", "xyz", "test1" );
        Event event2 = new Event("abd", EEventTypes.FOOD, 120, date1, "test2", "xyz", "test2" );


        Event return1 = eventService.createEvent(event1);
        Event return2 = eventService.createEvent(event2);

        Event fetch1 = eventService.getEventsByID("abc");
        List<Event> fetchAll = eventService.getAll();

        Assertions.assertNotNull(return1);
        Assertions.assertNotNull(return2);

        Assertions.assertNotEquals(return1, return2);

        Assertions.assertEquals("test1", fetch1.getEventName());
    }

    @Test
    public void feedbackService_createNewFeedback_argumentsAreNull() {
        Date date1 = new Date();
        Executable test1 = () -> new Event(null, null, 24, date1, "test1", "xyz", "test1" );
        Executable test2 = () -> new Event("abc", null, 0, date1, "test1", "xyz", "test1" );


        Assertions.assertThrows(IllegalArgumentException.class, test1);
        Assertions.assertThrows(IllegalArgumentException.class, test2);
    }

    /*@Test
    public void eventService_createevent_eventIsNull() {
        Event testEvent = eventService.createEvent(null);
    }*/

   /* @Test
    public void eventService_updateEvent_fetchEvent_valid() {
        Date date1 = new Date();
        Event event1 = new Event("abc", EEventTypes.FOOD, 24, date1, "test1", "xyz", "test1" );
        Event event2 = new Event("abd", EEventTypes.FOOD, 120, date1, "test2", "xyz", "test2" );

        Event eventC1 = eventService.createEvent(event1);
        Event eventC2 = eventService.createEvent(event2);

        Event eventUpate1 = new Event("abc", EEventTypes.FOOD, 100, date1, "test1 update", "xyz", "test1 update" );
        Event eventUpate2 = new Event("abd", EEventTypes.FOOD, 2, date1, "test2 update", "xyz", "test2 update" );

        Event eventU1 = eventService.updateEvent(eventUpate1);
        Event eventU2 = eventService.updateEvent(eventUpate2);

        Assertions.assertNotNull(eventC1);
        Assertions.assertNotNull(eventU1);
        Assertions.assertNotNull(eventC2);
        Assertions.assertNotNull(eventU2);

        Assertions.assertNotEquals(eventU1, eventC1);
        Assertions.assertNotEquals(eventU2, eventC2);

        Assertions.assertEquals(eventC1.getEventID(), eventU1.getEventID());
        Assertions.assertEquals(eventC2.getEventID(), eventU2.getEventID());

    }*/

    @Test
    void contextLoads() {
    }

}

