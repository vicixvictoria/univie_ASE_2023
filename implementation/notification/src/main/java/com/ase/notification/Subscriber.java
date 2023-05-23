package com.ase.notification;

import com.ase.common.RabbitMQMessage;
import com.ase.common.event.Event;
import com.ase.notification.model.NotificationService;
import com.ase.notification.model.data.EEventType;
import com.ase.notification.model.data.RawEvent;
import com.ase.notification.network.Converter;
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
        this.notificationService = notificationService;
    }

    private void registerNotification(String userId, String eventId, EEventType type) {
        LOGGER.info(String.format("Registered a new notification for the user %s for the event %s",
                userId, eventId));
        notificationService.addEvent(userId, eventId, type);
    }

    /**
     * Registers the user with the given id for a notification about the specified event
     *
     * @param userId  {@link String} contains the userId of the to be notified user
     * @param eventId {@link String} the eventId which was bookmarked
     */
    public void registerNotificationBookmarked(String userId, String eventId) {
        registerNotification(userId, eventId, EEventType.BOOKMARKED);
    }

    /**
     * Registers the user with the given id for a notification about the specified event
     *
     * @param userId  {@link String} contains the userId of the to be notified user
     * @param eventId {@link String} the eventId which was registered
     */
    public void registerNotificationRegistered(String userId, String eventId) {
        registerNotification(userId, eventId, EEventType.REGISTERED);
    }

    /**
     * Called when a new event is appended to the queue
     *
     * @param eventMessage {@link RabbitMQMessage} a rabbitmq message, containing an event and the
     *                     operation which was performed on it
     */
    public void eventConsumer(RabbitMQMessage<Event> eventMessage) {
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
