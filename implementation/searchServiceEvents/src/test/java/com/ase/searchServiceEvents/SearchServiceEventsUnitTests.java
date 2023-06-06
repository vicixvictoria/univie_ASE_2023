package com.ase.searchServiceEvents;


import com.ase.searchServiceEvents.integration.Subscriber;
import com.ase.searchServiceEvents.repository.ISearchServiceRepository;
import com.ase.searchServiceEvents.business.SearchServiceService;
import com.ase.searchServiceEvents.domain.EEventTypes;
import com.ase.searchServiceEvents.domain.Event;
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
class SearchServiceEventsUnitTests {

    @Mock
    private ISearchServiceRepository mockSearchServiceRepository;

    @Mock
    private Subscriber subscriber;

    private SearchServiceService searchServiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchServiceService = new SearchServiceService(mockSearchServiceRepository);
    }

    @Test
    void getAll_ShouldReturnAllEvents() {
        // Arrange
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findAll()).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getAll();

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findAll();
    }

    @Test
    void getAllEventsByorganizerID_ShouldReturnEventsByOrganizerID() {
        // Arrange
        String organizerID = "organizer123";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findAllByorganizerID(organizerID)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getAllEventsByorganizerID(organizerID);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findAllByorganizerID(organizerID);
    }

    @Test
    void getEventsByID_ShouldReturnEventByID() {
        // Arrange
        String eventID = "event123";
        Event expectedEvent = new Event();
        when(mockSearchServiceRepository.findEventByeventID(eventID)).thenReturn(expectedEvent);

        // Act
        Event result = searchServiceService.getEventsByID(eventID);

        // Assert
        assertEquals(expectedEvent, result);
        verify(mockSearchServiceRepository).findEventByeventID(eventID);
    }

    @Test
    void getEventsByDate_ShouldReturnEventsByDate() {
        // Arrange
        Date date = new Date();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findByDate(date)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getEventsByDate(date);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findByDate(date);
    }

    @Test
    void getEventsByCapacity_ShouldReturnEventsByCapacity() {
        // Arrange
        int capacity = 100;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findByCapacity(capacity)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getEventsByCapacity(capacity);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findByCapacity(capacity);
    }

    @Test
    void getEventsByName_ShouldReturnEventsByName() {
        // Arrange
        String name = "event name";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findByEventName(name)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getEventsByName(name);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findByEventName(name);
    }

    @Test
    void getEventsByType_ShouldReturnEventsByType() {
        // Arrange
        EEventTypes type = EEventTypes.FOOD;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findByType(type)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getEventsByType(type);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findByType(type);
    }

    @Test
    void getEventsByDescription_ShouldReturnEventsByDescription() {
        // Arrange
        String description = "event description";
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event());
        expectedEvents.add(new Event());
        when(mockSearchServiceRepository.findByDescription(description)).thenReturn(expectedEvents);

        // Act
        List<Event> result = searchServiceService.getEventsByDescription(description);

        // Assert
        assertEquals(expectedEvents, result);
        verify(mockSearchServiceRepository).findByDescription(description);
    }

    @Test
    void createEvent_ShouldSaveAndPublishNewEvent() {
        // Arrange
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        Event savedEvent = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        when(mockSearchServiceRepository.save(event)).thenReturn(savedEvent);

        // Act
        Event result = searchServiceService.createEvent(event);

        // Assert
        assertEquals(savedEvent, result);
        verify(mockSearchServiceRepository).save(event);
    }

    @Test
    void updateEvent_ShouldUpdateAndPublishEvent() {
        // Arrange
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        Event updatedEvent = new Event("abc", EEventTypes.HEALTH,3405,new Date(),"testing","abcdef","Test1Update");
        when(mockSearchServiceRepository.findEventByeventID(event.getEventID())).thenReturn(event);
        when(mockSearchServiceRepository.save(event)).thenReturn(updatedEvent);

        // Act
        Event result = searchServiceService.updateEvent(event);

        // Assert
        assertEquals(updatedEvent, result);
        verify(mockSearchServiceRepository).findEventByeventID(event.getEventID());
        verify(mockSearchServiceRepository).save(event);
    }

    @Test
    void deleteEvent_ShouldDeleteEvent() {
        // Arrange
        String eventID = "event123";
        Event event = new Event("event123", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");
        when(mockSearchServiceRepository.findEventByeventID(eventID)).thenReturn(event);

        // Act
        searchServiceService.deleteEvent(eventID);

        // Assert
        // verify(mockEventRepository).findEventByeventID(eventID);
        verify(mockSearchServiceRepository).deleteById(eventID);
    }
}
