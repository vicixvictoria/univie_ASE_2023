package com.ase.event;

import com.ase.event.domain.EEventTypes;
import com.ase.event.domain.Event;
import com.ase.event.dataAccess.IEventRepository;
import com.ase.event.business.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceUnitTests {

    @Mock
    private IEventRepository mockEventRepository;

    @Mock
    private Publisher mockPublisher;

    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(mockEventRepository, mockPublisher);
    }

    @Test
    void getAll_ShouldReturnAllEvents() {
        // Arrange
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findAll()).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getAll();

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findAll();
    }

    @Test
    void getAllEventsByorganizerID_ShouldReturnEventsByOrganizerID() {
        // Arrange
        String organizerID = "organizer123";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findAllByorganizerID(organizerID)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getAllEventsByorganizerID(organizerID);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findAllByorganizerID(organizerID);
    }

    @Test
    void getEventsByID_ShouldReturnEventByID() {
        // Arrange
        String eventID = "event123";
        Event expectedEvent = new Event();
        when(mockEventRepository.findEventByeventID(eventID)).thenReturn(expectedEvent);

        // Act
        Event result = eventService.getEventsByID(eventID);

        // Assert
        assertEquals(expectedEvent, result);
        verify(mockEventRepository).findEventByeventID(eventID);
    }

    @Test
    void getEventsByDate_ShouldReturnEventsByDate() {
        // Arrange
        Date date = new Date();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findByDate(date)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getEventsByDate(date);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findByDate(date);
    }

    @Test
    void getEventsByCapacity_ShouldReturnEventsByCapacity() {
        // Arrange
        int capacity = 100;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findByCapacity(capacity)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getEventsByCapacity(capacity);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findByCapacity(capacity);
    }

    @Test
    void getEventsByName_ShouldReturnEventsByName() {
        // Arrange
        String name = "event name";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findByEventName(name)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getEventsByName(name);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findByEventName(name);
    }

    @Test
    void getEventsByType_ShouldReturnEventsByType() {
        // Arrange
        EEventTypes type = EEventTypes.FOOD;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findByType(type)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getEventsByType(type);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findByType(type);
    }

    @Test
    void getEventsByDescription_ShouldReturnEventsByDescription() {
        // Arrange
        String description = "event description";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockEventRepository.findByDescription(description)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.getEventsByDescription(description);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockEventRepository).findByDescription(description);
    }

    @Test
    void createEvent_ShouldSaveAndPublishNewEvent() {
        // Arrange
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        Event savedEvent = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        when(mockEventRepository.save(event)).thenReturn(savedEvent);

        // Act
        Event result = eventService.createEvent(event);

        // Assert
        assertEquals(savedEvent, result);
        verify(mockEventRepository).save(event);
        verify(mockPublisher).newEvent(event);
    }

    @Test
    void updateEvent_ShouldUpdateAndPublishEvent() {
        // Arrange
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        Event updatedEvent = new Event("abc", EEventTypes.HEALTH,3405,new Date(),"testing","abcdef","Test1Update");
        when(mockEventRepository.findEventByeventID(event.getEventID())).thenReturn(event);
        when(mockEventRepository.save(event)).thenReturn(updatedEvent);

        // Act
        Event result = eventService.updateEvent(event);

        // Assert
        assertEquals(updatedEvent, result);
        verify(mockEventRepository).findEventByeventID(event.getEventID());
        verify(mockEventRepository).save(event);
        verify(mockPublisher).updateEvent(updatedEvent);
    }

    @Test
    void deleteEvent_ShouldDeleteEvent() {
        // Arrange
        String eventID = "event123";
        Event event = new Event("event123", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        when(mockEventRepository.findEventByeventID(eventID)).thenReturn(event);

        // Act
        eventService.deleteEvent(eventID);

        // Assert
       // verify(mockEventRepository).findEventByeventID(eventID);
        verify(mockEventRepository).deleteById(eventID);
        verify(mockPublisher).deleteEvent(event);
    }
}
