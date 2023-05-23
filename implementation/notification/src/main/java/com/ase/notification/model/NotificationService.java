package com.ase.notification.model;

import com.ase.notification.model.data.EEventType;
import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.data.RawEvent;
import com.ase.notification.model.notificationsendtime.INotificationSendTime;
import com.ase.notification.model.repository.IEventRepository;
import com.ase.notification.model.repository.INotificationEventRepository;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    //TODO: remove this once I can get the correct user object into this class
    @Value("${NOTIFICATION_TARGET_EMAIL:advancedsoftwareengineer@gmail.com}")
    private String notificationTargetEmail;

    private final INotificationEventRepository notificationEventRepository;
    private final IEventRepository eventRepository;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final NotificationSender notificationSender;
    private final INotificationSendTime notificationSendTime;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    /**
     * Constructor, for the NotificationService
     *
     * @param notificationEventRepository Autowired, used to store all notificationEvents
     * @param eventRepository             Autowired, used to store a copy of all Events
     * @param taskScheduler               Autowired, used to schedule notifications
     * @param notificationSender          Autowired, used to send notifications
     * @param notificationSendTime        Autowired, used to determine when to send the reminder
     *                                    notification
     */
    @Autowired
    public NotificationService(INotificationEventRepository notificationEventRepository,
            IEventRepository eventRepository,
            ThreadPoolTaskScheduler taskScheduler,
            NotificationSender notificationSender,
            @Qualifier("day-before") INotificationSendTime notificationSendTime) {
        this.notificationEventRepository = notificationEventRepository;
        this.eventRepository = eventRepository;
        this.taskScheduler = taskScheduler;
        this.notificationSender = notificationSender;
        this.notificationSendTime = notificationSendTime;

        // This is used when an event gets updated, to cancel the scheduled notification
        this.taskScheduler.setRemoveOnCancelPolicy(true);
    }

    private void scheduleNotification(NotificationEvent event) {
        // TODO: We will need the future to be able to call `future.cancel(false)` to be able to cancel scheduled
        //  notifications. Thus, we will need to save it somewhere
        var future = taskScheduler.schedule(
                () -> notificationSender.sendUpcoming(event), notificationSendTime.get(event));
    }

    /**
     * Adds a notification for the user with the given ID and the given event
     *
     * @param userId  the id of the user who will receive a notification
     * @param eventId the id of the event about which will be notified
     * @param type    the type of the event
     */
    public void addEvent(String userId, String eventId, EEventType type) {
        Optional<RawEvent> rawEvent = eventRepository.findById(eventId);

        if (rawEvent.isEmpty()) {
            LOGGER.error(String.format(
                    "Tried registering an event which is not in the database! EventId: %s",
                    eventId));
            assert false;
            return;
        }

        NotificationEvent event = new NotificationEvent(rawEvent.get(), userId, type);

        scheduleNotification(event);
        notificationEventRepository.save(event);
    }

    /**
     * Updates the given event and sends a notification to all users, who are associated with the
     * event
     *
     * @param event the event to be updated, must have the same Id as the to be updated event
     */
    public void updateEvent(RawEvent event) {
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
                            "Tried updating the event with id %s, but no notification registered for this event"),
                    event.getId());
            return;
        }

        Collection<NotificationEvent> updatedEvents = updatedEventsResult.get();

        // TODO: remove old taskScheduler

        for (NotificationEvent notificationEvent : updatedEvents) {
            scheduleNotification(notificationEvent);
            notificationSender.sendUpdated(notificationEvent);
        }
    }

    /**
     * Saves a new event into the repository
     *
     * @param event the event to be saved
     */
    public void newEvent(RawEvent event) {
        eventRepository.save(event);
    }

    /**
     * Deletes an event from the repository
     *
     * @param event the event to be deleted
     */
    public void deleteEvent(RawEvent event) {
        eventRepository.delete(event);
    }
}
