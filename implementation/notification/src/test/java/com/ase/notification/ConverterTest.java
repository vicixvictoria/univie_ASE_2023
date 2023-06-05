package com.ase.notification;

import com.ase.common.event.Event;
import com.ase.notification.model.data.RawEvent;
import com.ase.notification.network.Converter;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConverterTest {

    private Converter converter;

    @BeforeEach
    void setUp() {
        converter = new Converter();
    }

    @Test
    void getLocalEvent_shouldReturnRawEventWithCorrectValues() {
        // Arrange
        Event event = mock(Event.class);
        String eventID = "eventId";
        String eventName = "Test Event";
        String description = "This is a test event";
        Date date = new Date();

        // Set up the mock Event object
        when(event.eventID()).thenReturn(eventID);
        when(event.eventName()).thenReturn(eventName);
        when(event.description()).thenReturn(description);
        when(event.date()).thenReturn(date);

        // Act
        RawEvent rawEvent = converter.getLocalEvent(event);

        // Assert
        assertEquals(eventID, rawEvent.getId());
        assertEquals(eventName, rawEvent.getName());
        assertEquals(description, rawEvent.getDescription());
        assertEquals(date, rawEvent.getEventDate());
    }

    @Test
    void getLocalEvent_withEmptyEvent_shouldReturnRawEventWithDefaultValues() {
        // Arrange
        Event event = mock(Event.class);

        // Set up the mock Event object with empty values
        when(event.eventID()).thenReturn("id");
        when(event.eventName()).thenReturn("");
        when(event.description()).thenReturn("");
        when(event.date()).thenReturn(new Date());

        // Act
        RawEvent rawEvent = converter.getLocalEvent(event);

        // Assert
        assertEquals("id", rawEvent.getId());
        assertEquals("", rawEvent.getName());
        assertEquals("", rawEvent.getDescription());
        assertEquals(event.date(), rawEvent.getEventDate());
    }

    @Test
    void getLocalEvent_withEventWithNullValues_shouldReturnRawEventWithNullValues() {
        // Arrange
        Event event = mock(Event.class);

        // Set up the mock Event object with null values
        when(event.eventID()).thenReturn(null);
        when(event.eventName()).thenReturn(null);
        when(event.description()).thenReturn(null);
        when(event.date()).thenReturn(null);

        // Act
        RawEvent rawEvent = converter.getLocalEvent(event);

        // Assert
        assertNull(rawEvent.getId());
        assertNull(rawEvent.getName());
        assertNull(rawEvent.getDescription());
        assertNull(rawEvent.getEventDate());
    }
}
