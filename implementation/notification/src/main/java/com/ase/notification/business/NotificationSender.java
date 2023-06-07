package com.ase.notification.business;

import com.ase.notification.integration.Publisher;
import com.ase.notification.domain.NotificationEvent;
import com.ase.notification.business.notificationcreation.INotificationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationSender {
    private final INotificationCreator notificationCreatorUpcoming;
    private final INotificationCreator notificationCreatorUpdated;

    private final Publisher publisher;

    /**
     * Constructor for the NotificationSender class
     *
     * @param notificationCreatorUpcoming Object, which encodes how an "upcoming" notification is
     *                                    supposed to be generated
     * @param notificationCreatorUpdated  Object, which encodes how an "updated" notification is
     *                                    supposed to be generated
     * @param publisher                   Object, capable of publishing messages to RabbitMQ
     *                                    exchanges
     */
    @Autowired
    public NotificationSender(
            @Qualifier("placeholder") INotificationCreator notificationCreatorUpcoming,
            @Qualifier("placeholder") INotificationCreator notificationCreatorUpdated,
            Publisher publisher) {
        this.notificationCreatorUpcoming = notificationCreatorUpcoming;
        this.notificationCreatorUpdated = notificationCreatorUpdated;
        this.publisher = publisher;
    }

    private void send(NotificationEvent event, INotificationCreator notificationCreator) {
        publisher.publishNotification(event.getUserId(), notificationCreator.create(event));
    }

    /**
     * Send a notification about an "upcoming"
     *
     * @param event the event about which will be notified
     */
    public void sendUpcoming(NotificationEvent event) {
        send(event, notificationCreatorUpcoming);
    }

    /**
     * Send a notification about an "updated" event
     *
     * @param event the event about which will be notified
     */
    public void sendUpdated(NotificationEvent event) {
        send(event, notificationCreatorUpdated);
    }
}
