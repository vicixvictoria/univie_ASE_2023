package com.ase.notification;

import com.ase.notification.domain.RawEvent;
import com.ase.notification.dataAccess.IEventRepository;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class IEventRepositoryTest {

    private final String eventName = "event name";
    private final String eventDescription = "event description";
    private final Date eventDate = Date.from(Instant.now());

    @Autowired
    private IEventRepository eventRepository;

    private RawEvent getRawEvent() {
        return new RawEvent(UUID.randomUUID().toString(), eventName, eventDescription, eventDate);
    }

    @Test
    public void IEventRepository_saveEvent_validCall() {
        RawEvent event = getRawEvent();
        eventRepository.save(event);
    }

    @Test
    public void IEventRepository_saveEvent_checkContains() {
        RawEvent event = getRawEvent();
        eventRepository.save(event);
        Assertions.assertTrue(eventRepository.existsById(event.getId()));
    }

    @Test
    public void IEventRepository_findEventById_existingId_shouldReturnEvent() {
        RawEvent event = getRawEvent();
        eventRepository.save(event);

        RawEvent retrievedEvent = eventRepository.findById(event.getId()).orElse(null);
        Assertions.assertNotNull(retrievedEvent);
        Assertions.assertEquals(event.getId(), retrievedEvent.getId());
    }

    @Test
    public void IEventRepository_findEventById_nonExistingId_shouldReturnNull() {
        RawEvent retrievedEvent = eventRepository.findById("nonExistingId").orElse(null);
        Assertions.assertNull(retrievedEvent);
    }

    @Test
    public void IEventRepository_deleteEvent_validCall() {
        RawEvent event = getRawEvent();
        eventRepository.save(event);
        eventRepository.flush();

        eventRepository.delete(event);
        RawEvent deletedEvent = eventRepository.findById(event.getId()).orElse(null);
        Assertions.assertNull(deletedEvent);
    }

    @Test
    public void IEventRepository_countEvents_shouldReturnCorrectCount() {
        RawEvent event1 = getRawEvent();
        RawEvent event2 = getRawEvent();
        RawEvent event3 = getRawEvent();

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        long count = eventRepository.count();
        Assertions.assertEquals(3, count);
    }

    @Test
    public void IEventRepository_updateName_shouldUpdate() {
        RawEvent event = getRawEvent();
        eventRepository.save(event);
        eventRepository.flush();

        RawEvent dbEvent = eventRepository.findById(event.getId()).orElse(null);
        Assertions.assertNotNull(dbEvent);

        dbEvent.setName("other name");
        eventRepository.flush();

        RawEvent afterUpdateDbEvent = eventRepository.findById(event.getId()).orElse(null);
        Assertions.assertNotNull(afterUpdateDbEvent);

        Assertions.assertEquals("other name", afterUpdateDbEvent.getName());
    }

}
