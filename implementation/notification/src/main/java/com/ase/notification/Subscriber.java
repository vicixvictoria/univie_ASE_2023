package com.ase.notification;

import com.ase.notification.model.NotificationService;
import com.ase.notification.model.data.NotificationEvent;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Defines the callback methods called when a new RabbitMQ message is received. The RabbitMQ
 * configuration, including the binding of the callback methods can be found in
 * {@link RabbitMQConfiguration}. Can be seen as a parallel to
 * {@link org.springframework.web.bind.annotation.RestController}
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

    /**
     * Registers the user with the given id for a notification about the specified event
     *
     * @param userId {@link String} contains the userId of the to be notified user
     * @param event  {@link NotificationEvent} contains the event about which will be notified
     */
    public void registerNotification(String userId, NotificationEvent event) {
        LOGGER.info(String.format("Registered a new notification for the user %s for the vent %s",
                userId, event.getName()));
        notificationService.addEvent(userId, event);
    }

    /**
     * Updates the event if it is stored
     *
     * @param updatedEvent {@link NotificationEvent} the event to be updated. Has to have the same
     *                     ID as an already saved event.
     */
    public void eventUpdated(NotificationEvent updatedEvent) {
        LOGGER.info(String.format("Received an updated event with the name %s",
                updatedEvent.getName()));
        notificationService.updateEvent(updatedEvent);
    }
}
