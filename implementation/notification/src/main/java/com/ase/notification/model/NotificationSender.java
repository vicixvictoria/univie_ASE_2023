package com.ase.notification.model;

import com.ase.common.sendNotification.NotificationContent;
import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.notificationcreation.INotificationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationSender {

    // TODO: this will need to be reworked to work with rabbitMQ

    private final INotificationCreator notificationCreatorUpcoming;
    private final INotificationCreator notificationCreatorUpdated;


    private static final String SEND_NOTIFICATION_ENDPOINT = "http://localhost:8080/api/v1/sendNotification";

    /**
     * Constructor for the NotificationSender class
     *
     * @param notificationCreatorUpcoming Object, which encodes how an "upcoming" notification is
     *                                    supposed to be generated
     * @param notificationCreatorUpdated  Object, which encodes how an "updated" notification is
     *                                    supposed to be generated
     */
    @Autowired
    public NotificationSender(
            @Qualifier("placeholder") INotificationCreator notificationCreatorUpcoming,
            @Qualifier("placeholder") INotificationCreator notificationCreatorUpdated) {
        this.notificationCreatorUpcoming = notificationCreatorUpcoming;
        this.notificationCreatorUpdated = notificationCreatorUpdated;
    }

    private void send(NotificationEvent event, INotificationCreator notificationCreator) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(SEND_NOTIFICATION_ENDPOINT,
                new NotificationContent(event.getUserId(), notificationCreator.create(event)));
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
