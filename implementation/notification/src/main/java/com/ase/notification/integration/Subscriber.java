package com.ase.notification.integration;

import com.ase.common.RabbitMQMessage;
import com.ase.common.attendance.AttendanceMessage;
import com.ase.common.bookmarkEvent.BookmarkEventMessage;
import com.ase.common.event.Event;
import com.ase.notification.business.NotificationService;
import com.ase.notification.domain.EEventType;
import com.ase.notification.domain.RawEvent;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Defines the callback methods called when a new RabbitMQ message is received. Can be seen as a
 * parallel to {@link org.springframework.web.bind.annotation.RestController}
 */
@Component
public class Subscriber {

    private final NotificationService notificationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());


    @Autowired
    public Subscriber(NotificationService notificationService) {
        assert notificationService != null;
        this.notificationService = notificationService;
    }

    private void registerNotification(String userId, String eventId, EEventType type) {
        LOGGER.info(String.format("Registered a new notification for the user %s for the event %s",
                userId, eventId));
        notificationService.addNotificationEvent(userId, eventId, type);
    }

    private void updateNotification(String userId, String eventId, EEventType type) {
        LOGGER.error("The bookmarked event should not get updated ever!");
        assert false;
    }

    private void deleteNotification(String userId, String eventId, EEventType type) {
        LOGGER.info(String.format("Deleted a notification for the user %s for the event %s",
                userId, eventId));
        notificationService.deleteNotificationEvent(userId, eventId, type);
    }

    /**
     * Registers the user with the given id for a notification about the specified event
     *
     * @param bookmarkMessage contains the messaged bookmark object
     */
    public void registerNotificationBookmarked(
            RabbitMQMessage<BookmarkEventMessage> bookmarkMessage) {
        assert bookmarkMessage != null;

        BookmarkEventMessage bookmark = bookmarkMessage.getContent();
        assert bookmark != null;

        switch (bookmarkMessage.getMessageType()) {
            case NEW -> registerNotification(bookmark.userId(), bookmark.eventId(),
                    EEventType.BOOKMARKED);
            case UPDATE -> updateNotification(bookmark.userId(), bookmark.eventId(),
                    EEventType.BOOKMARKED);
            case DELETE -> deleteNotification(bookmark.userId(), bookmark.eventId(),
                    EEventType.BOOKMARKED);
        }
    }

    /**
     * Registers the user with the given id for a notification about the specified event
     *
     * @param registeredMessage contains the messaged registered object
     */
    public void registerNotificationRegistered(
            RabbitMQMessage<AttendanceMessage> registeredMessage) {
        assert registeredMessage != null;

        AttendanceMessage registered = registeredMessage.getContent();

        switch (registeredMessage.getMessageType()) {
            case NEW -> registerNotification(registered.userID(), registered.eventID(),
                    EEventType.REGISTERED);
            case UPDATE -> updateNotification(registered.userID(), registered.eventID(),
                    EEventType.REGISTERED);
            case DELETE -> deleteNotification(registered.userID(), registered.eventID(),
                    EEventType.BOOKMARKED);
        }
    }

    /**
     * Called when a new event is appended to the queue
     *
     * @param eventMessage {@link RabbitMQMessage} a rabbitmq message, containing an event and the
     *                     operation which was performed on it
     */
    public void eventConsumer(RabbitMQMessage<Event> eventMessage) {
        assert eventMessage != null;
        LOGGER.info("Received an event message");

        Converter converter = new Converter();
        RawEvent event = converter.getLocalEvent(eventMessage.getContent());

        switch (eventMessage.getMessageType()) {
            case NEW -> notificationService.newEvent(event);
            case UPDATE -> notificationService.updateEvent(event);
            case DELETE -> notificationService.deleteEvent(event);
        }
    }
}
