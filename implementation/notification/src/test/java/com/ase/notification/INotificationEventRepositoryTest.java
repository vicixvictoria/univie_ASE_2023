package com.ase.notification;

import com.ase.notification.domain.EEventType;
import com.ase.notification.domain.IUnmodifiableRawEvent;
import com.ase.notification.domain.NotificationEvent;
import com.ase.notification.domain.RawEvent;
import com.ase.notification.dataAccess.IEventRepository;
import com.ase.notification.dataAccess.INotificationEventRepository;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class INotificationEventRepositoryTest {

    private final String eventName = "event name";
    private final String eventDescription = "event description";
    private final Date eventDate = Date.from(Instant.now());

    private final String userId = "userId";
    private final EEventType type = EEventType.REGISTERED;

    @Autowired
    private INotificationEventRepository notificationEventRepository;

    @Autowired
    private IEventRepository eventRepository;

    private RawEvent getRawEvent() {
        RawEvent event = new RawEvent(UUID.randomUUID().toString(), eventName, eventDescription,
                eventDate);
        eventRepository.save(event);
        return eventRepository.findById(event.getId()).orElse(null);
    }

    private NotificationEvent getNotificationEvent() {
        return new NotificationEvent(getRawEvent(), userId, type);
    }

    @Test
    public void INotificationEventRepository_saveNotificationEvent_validCall() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);
    }

    @Test
    public void INotificationEventRepository_saveEvent_checkContains() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);
        Assertions.assertTrue(notificationEventRepository.existsById(notificationEvent.getId()));
    }

    @Test
    public void INotificationEventRepository_findEventById_existingId_shouldReturnEvent() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);

        NotificationEvent retrievedEvent = notificationEventRepository.findById(
                notificationEvent.getId()).orElse(null);
        Assertions.assertNotNull(retrievedEvent);
        Assertions.assertEquals(notificationEvent.getId(), retrievedEvent.getId());
    }

    @Test
    public void INotificationEventRepository_findEventById_nonExistingId_shouldReturnNull() {
        NotificationEvent retrievedEvent = notificationEventRepository.findById("nonExistingId")
                .orElse(null);
        Assertions.assertNull(retrievedEvent);
    }

    @Test
    public void INotificationEventRepository_deleteEvent_validCall() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);
        notificationEventRepository.flush();

        notificationEventRepository.delete(notificationEvent);
        NotificationEvent deletedEvent = notificationEventRepository.findById(
                notificationEvent.getId()).orElse(null);
        Assertions.assertNull(deletedEvent);
    }

    @Test
    public void INotificationEventRepository_countEvents_shouldReturnCorrectCount() {
        NotificationEvent notificationEvent1 = getNotificationEvent();
        NotificationEvent notificationEvent2 = getNotificationEvent();
        NotificationEvent notificationEvent3 = getNotificationEvent();

        notificationEventRepository.save(notificationEvent1);
        notificationEventRepository.save(notificationEvent2);
        notificationEventRepository.save(notificationEvent3);

        long count = notificationEventRepository.count();
        Assertions.assertEquals(3, count);
    }

    @Test
    public void INotificationEventRepository_updateName_shouldUpdate() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);
        notificationEventRepository.flush();

        NotificationEvent dbEvent = notificationEventRepository.findById(notificationEvent.getId())
                .orElse(null);
        Assertions.assertNotNull(dbEvent);

        dbEvent.setUserId("otherId");
        notificationEventRepository.flush();

        NotificationEvent afterUpdateDbEvent = notificationEventRepository.findById(
                notificationEvent.getId()).orElse(null);
        Assertions.assertNotNull(afterUpdateDbEvent);

        Assertions.assertEquals("otherId", afterUpdateDbEvent.getUserId());
    }

    @Test
    public void INotificationEventRepository_violateUniqueCondition_shouldFail() {
        NotificationEvent notificationEvent1 = getNotificationEvent();
        NotificationEvent notificationEvent2 = getNotificationEvent();

        notificationEventRepository.save(notificationEvent1);
        notificationEventRepository.save(notificationEvent2);
        notificationEventRepository.flush();

        NotificationEvent dbNotificationEvent2 = notificationEventRepository.findById(
                notificationEvent2.getId()).orElse(null);
        Assertions.assertNotNull(dbNotificationEvent2);

        IUnmodifiableRawEvent unmodifiableEvent1 = notificationEvent1.getEvent();
        RawEvent event1 = new RawEvent(unmodifiableEvent1.getId(), unmodifiableEvent1.getName(),
                unmodifiableEvent1.getDescription(), unmodifiableEvent1.getEventDate());

        Executable exec = () -> {
            dbNotificationEvent2.setEvent(event1);
            notificationEventRepository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, exec);
    }

    @Test
    public void INotificationEventRepository_findNotificationEventsByEventAndUserIdAndType_validCall() {
        NotificationEvent notificationEvent = getNotificationEvent();
        notificationEventRepository.save(notificationEvent);

        NotificationEvent dbNotificationEvent = notificationEventRepository.findNotificationEventsByEventAndUserIdAndType(
                notificationEvent.getEvent(), notificationEvent.getUserId(),
                notificationEvent.getType()).orElse(null);
        Assertions.assertNotNull(dbNotificationEvent);

        Assertions.assertEquals(notificationEvent.getId(), dbNotificationEvent.getId());
    }

    @Test
    public void INotificationEventRepository_findNotificationEventsByEvent_validCall() {
        NotificationEvent notificationEvent1 = getNotificationEvent();
        NotificationEvent notificationEvent2 = getNotificationEvent();
        NotificationEvent notificationEvent3 = getNotificationEvent();

        notificationEvent2.setUserId(notificationEvent2.getUserId() + "2");
        notificationEvent3.setUserId(notificationEvent3.getUserId() + "3");

        IUnmodifiableRawEvent unmodifiableEvent1 = notificationEvent1.getEvent();
        RawEvent event1 = new RawEvent(unmodifiableEvent1.getId(), unmodifiableEvent1.getName(),
                unmodifiableEvent1.getDescription(), unmodifiableEvent1.getEventDate());

        notificationEvent2.setEvent(event1);
        notificationEvent3.setEvent(event1);

        notificationEventRepository.save(notificationEvent1);
        notificationEventRepository.save(notificationEvent2);
        notificationEventRepository.save(notificationEvent3);

        Collection<NotificationEvent> notificationEvents = notificationEventRepository.findNotificationEventsByEvent(
                event1).orElse(null);

        Assertions.assertEquals(3, notificationEvents.size());
        Assertions.assertTrue(notificationEvents.contains(notificationEvent1));
        Assertions.assertTrue(notificationEvents.contains(notificationEvent2));
        Assertions.assertTrue(notificationEvents.contains(notificationEvent3));
    }

}
