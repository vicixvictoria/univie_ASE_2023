package com.ase.notification.notification;

import static org.mockito.Mockito.when;

import com.ase.notification.model.NotificationScheduler;
import com.ase.notification.model.NotificationSender;
import com.ase.notification.model.NotificationService;
import com.ase.notification.model.data.EEventType;
import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.data.NotificationEventFactory;
import com.ase.notification.model.data.RawEvent;
import com.ase.notification.model.repository.IEventRepository;
import com.ase.notification.model.repository.INotificationEventRepository;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

public class NotificationServiceTest {

    // TODO: finish later. The signatures of the methods might still change
    private INotificationEventRepository notificationEventRepository;
    private NotificationEvent containedNotificationEvent;
    private IEventRepository eventRepository;
    private String containedEventId;
    private RawEvent containedEvent;
    private String containedAndSavedId;
    private RawEvent containedAndSaved;

    private Collection<NotificationEvent> returnedEvents;

    private NotificationSender notificationSender;

    private NotificationEventFactory notificationEventFactory;

    private NotificationScheduler notificationScheduler;

    private NotificationService notificationService;

    @BeforeEach
    public void setNotificationService() {
        notificationEventRepository = Mockito.mock(INotificationEventRepository.class);
        containedNotificationEvent = Mockito.mock(NotificationEvent.class);
        when(notificationEventRepository.save(containedNotificationEvent)).thenThrow(
                new DataIntegrityViolationException("already contained"));

        eventRepository = Mockito.mock(IEventRepository.class);

        containedEventId = "contained-id";
        containedAndSavedId = "contained-and-saved-id";

        containedEvent = Mockito.mock(RawEvent.class);
        when(containedEvent.getId()).thenReturn(containedEventId);
        containedAndSaved = Mockito.mock(RawEvent.class);

        when(notificationEventRepository
                .findNotificationEventsByEventAndUserIdAndType(Mockito.eq(containedEvent),
                        Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(containedNotificationEvent));

        when(eventRepository.findById(containedEventId)).thenReturn(Optional.of(containedEvent));
        when(eventRepository.findById(containedAndSavedId)).thenReturn(
                Optional.of(containedAndSaved));
        when(eventRepository.save(containedEvent)).thenThrow(
                new DataIntegrityViolationException("already contained"));

        notificationSender = Mockito.mock(NotificationSender.class);

        notificationEventFactory = Mockito.mock(NotificationEventFactory.class);
        when(notificationEventFactory.get(Mockito.eq(containedAndSaved), Mockito.any(),
                Mockito.any()))
                .thenReturn(containedNotificationEvent);

        notificationScheduler = Mockito.mock(NotificationScheduler.class);

        notificationService = new NotificationService(
                notificationEventRepository,
                eventRepository,
                notificationSender,
                notificationEventFactory,
                notificationScheduler
        );
    }


    @Test
    public void NotificationService_addNotificationEvent_validCall() {
        String userId = "some-id";
        EEventType eventType = EEventType.REGISTERED;

        notificationService.addNotificationEvent(userId, containedEventId, eventType);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(containedEventId);
        Mockito.verify(notificationScheduler, Mockito.times(1)).schedule(Mockito.any());
        Mockito.verify(notificationEventRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void NotificationService_addNotificationEvent_emptyUserId_validCall() {
        String userId = "";
        EEventType eventType = EEventType.REGISTERED;

        notificationService.addNotificationEvent(userId, containedEventId, eventType);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(containedEventId);
        Mockito.verify(notificationScheduler, Mockito.times(1)).schedule(Mockito.any());
        Mockito.verify(notificationEventRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void NotificationService_addNotificationEvent_notContainedEventId_shouldThrow() {
        String eventId = "non-contained-id";
        String userId = "some-id";
        EEventType eventType = EEventType.REGISTERED;

        Executable exec = () -> notificationService.addNotificationEvent(userId, eventId,
                eventType);

        Assertions.assertThrows(AssertionError.class, exec);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(notificationScheduler, Mockito.times(0)).schedule(Mockito.any());
        Mockito.verify(notificationEventRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void NotificationService_addNotificationEvent_saveAlreadySaved_shouldThrow() {
        String userId = "some-id";
        EEventType eventType = EEventType.REGISTERED;

        // try saving second time
        Executable exec = () -> notificationService.addNotificationEvent(userId,
                containedAndSavedId,
                eventType);

        Assertions.assertThrows(AssertionError.class, exec);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(notificationEventRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(notificationScheduler, Mockito.times(0)).schedule(Mockito.any());
    }

    @Test
    public void NotificationService_deleteNotificationEvent_validCall() {

        String userId = "some-id";
        notificationService.deleteNotificationEvent(userId, containedEventId,
                EEventType.REGISTERED);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(containedEventId);
        Mockito.verify(notificationEventRepository, Mockito.times(1))
                .delete(containedNotificationEvent);
        Mockito.verify(notificationScheduler, Mockito.times(1))
                .cancelScheduled(containedNotificationEvent);
    }

    @Test
    public void NotificationService_deleteNotificationEvent_notContainedEvent_shouldThrow() {

        String userId = "some-id";
        String eventId = "other-id";
        Executable exec = () -> notificationService
                .deleteNotificationEvent(userId, eventId, EEventType.REGISTERED);

        Assertions.assertThrows(AssertionError.class, exec);

        Mockito.verify(eventRepository, Mockito.times(1)).findById(eventId);
        Mockito.verify(notificationEventRepository, Mockito.times(0))
                .delete(Mockito.any());
        Mockito.verify(notificationScheduler, Mockito.times(0))
                .cancelScheduled(Mockito.any());
    }

    @Test
    public void NotificationService_newEvent_validCall() {
        RawEvent newEvent = Mockito.mock(RawEvent.class);
        notificationService.newEvent(newEvent);
        Mockito.verify(eventRepository, Mockito.times(1)).save(newEvent);
        Mockito.verify(notificationScheduler, Mockito.times(0)).schedule(Mockito.any());
    }

    @Test
    public void NotificationService_newEvent_alreadyContained_shouldThrow() {
        Executable exec = () -> notificationService.newEvent(containedEvent);
        Assertions.assertThrows(AssertionError.class, exec);
        Mockito.verify(notificationScheduler, Mockito.times(0)).schedule(Mockito.any());
    }

    @Test
    public void NotificationService_deleteEvent_validCall() {
        notificationService.deleteEvent(containedEvent);
        Mockito.verify(eventRepository, Mockito.times(1)).delete(containedEvent);
    }

    @Test
    public void NotificationService_updateEvent_validCall() {
        notificationService.updateEvent(containedEvent);
    }

}
