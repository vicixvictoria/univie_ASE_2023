package com.ase.notification.business;

import com.ase.notification.domain.EEventType;
import com.ase.notification.domain.NotificationEvent;
import com.ase.notification.domain.NotificationEventFactory;
import com.ase.notification.domain.RawEvent;
import com.ase.notification.dataAccess.IEventRepository;
import com.ase.notification.dataAccess.INotificationEventRepository;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final INotificationEventRepository notificationEventRepository;
    private final IEventRepository eventRepository;
    private final NotificationSender notificationSender;
    private final NotificationEventFactory notificationEventFactory;
    private final NotificationScheduler scheduler;


    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    /**
     * Constructor, for the NotificationService
     *
     * @param notificationEventRepository Autowired, used to store all notificationEvents
     * @param eventRepository             Autowired, used to store a copy of all Events
     * @param notificationSender          Autowired, used to send notifications notification
     * @param notificationEventFactory    Autowired, mainly used to allow for easier testing.
     *                                    Creates new {@link NotificationEvent} objects.
     */
    @Autowired
    public NotificationService(INotificationEventRepository notificationEventRepository,
            IEventRepository eventRepository,
            NotificationSender notificationSender,
            NotificationEventFactory notificationEventFactory,
            NotificationScheduler scheduler) {

        assert notificationEventRepository != null;
        assert eventRepository != null;
        assert notificationSender != null;

        this.notificationEventRepository = notificationEventRepository;
        this.eventRepository = eventRepository;
        this.notificationSender = notificationSender;
        this.notificationEventFactory = notificationEventFactory;
        this.scheduler = scheduler;
    }

    /**
     * Adds a notification for the user with the given ID and the given event
     *
     * @param userId  the id of the user who will receive a notification
     * @param eventId the id of the event about which will be notified
     * @param type    the type of the event
     */
    public void addNotificationEvent(String userId, String eventId, EEventType type) {
        assert userId != null;
        assert eventId != null;
        assert type != null;

        Optional<RawEvent> rawEvent = eventRepository.findById(eventId);

        if (rawEvent.isEmpty()) {
            LOGGER.error(String.format(
                    "Tried registering an event which is not in the database! EventId: %s",
                    eventId));
            assert false;
            return;
        }

        NotificationEvent event = notificationEventFactory.get(rawEvent.get(), userId, type);

        try {
            notificationEventRepository.save(event);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(String.format(
                    "Tried adding a notification which already exist. This should not happen! EventId: %s, UserId: %s",
                    eventId, userId));
            assert false;
            return; // fail gracefully in production
        }

        scheduler.schedule(event);
    }

    /**
     * Deletes the NotificationEvent based on the given parameters. Also cancels any scheduled
     * notifications for this event
     *
     * @param userId  the id of the user who would have been receiving a notification
     * @param eventId the id of the event which would have been notified about
     * @param type    the type of the notification
     */
    public void deleteNotificationEvent(String userId, String eventId, EEventType type) {
        assert userId != null;
        assert eventId != null;
        assert type != null;

        Optional<RawEvent> rawEvent = eventRepository.findById(eventId);

        if (rawEvent.isEmpty()) {
            LOGGER.error(String.format(
                    "Tried registering an event which is not in the database! EventId: %s",
                    eventId));
            assert false;
            return;
        }

        Optional<NotificationEvent> event = notificationEventRepository
                .findNotificationEventsByEventAndUserIdAndType(rawEvent.get(), userId, type);

        if (event.isEmpty()) {
            LOGGER.error(String.format(
                    "Tried deleting an event which is not in the database! EventId: %s", eventId));
            assert false;
            return;
        }

        scheduler.cancelScheduled(event.get());
        notificationEventRepository.delete(event.get());
    }

    /**
     * Updates the given event and sends a notification to all users, who are associated with the
     * event
     *
     * @param event the event to be updated, must have the same Id as the to be updated event
     */
    public void updateEvent(RawEvent event) {
        assert event != null;

        Optional<RawEvent> oldEventResult = eventRepository.findById(event.getId());

        if (oldEventResult.isEmpty()) {
            LOGGER.error(String.format(
                    "An update request for an entity which does not exist was called. Failing gracefully. EventID: %s",
                    event.getId()));
            assert false;
            return;
        }

        RawEvent oldEvent = oldEventResult.get();
        oldEvent.update(event);
        // flush the database to make sure the event is actually updated as we will be querying
        //  NotificationEvents, containing that event in the next step
        eventRepository.flush();

        Optional<Collection<NotificationEvent>> updatedEventsResult =
                notificationEventRepository.findNotificationEventsByEvent(event);

        if (updatedEventsResult.isEmpty()) {
            LOGGER.info(String.format(
                    "Tried updating the event with id %s, but no notification registered for this event",
                    event.getId()));
            return;
        }

        Collection<NotificationEvent> updatedEvents = updatedEventsResult.get();

        for (NotificationEvent notificationEvent : updatedEvents) {
            scheduler.cancelScheduled(notificationEvent);
            scheduler.schedule(notificationEvent);

            notificationSender.sendUpdated(notificationEvent);
        }
    }

    /**
     * Saves a new event into the repository
     *
     * @param event the event to be saved
     */
    public void newEvent(RawEvent event) {
        assert event != null;

        try {
            eventRepository.save(event);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(
                    String.format("Tried saving an event, which is already contained. EventId: %s",
                            event.getId())
            );
            assert false;
        }
    }

    /**
     * Deletes an event from the repository
     *
     * @param event the event to be deleted
     */
    public void deleteEvent(RawEvent event) {
        assert event != null;

        try {
            eventRepository.delete(event);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(
                    String.format("Tried deleting an event, which does not exist. EventId: %s",
                            event.getId())
            );
            assert false;
        }
    }
}
