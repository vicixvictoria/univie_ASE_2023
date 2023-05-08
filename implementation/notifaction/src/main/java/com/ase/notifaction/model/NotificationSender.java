package com.ase.notifaction.model;

import com.ase.notifaction.model.data.NotificationContent;
import com.ase.notifaction.model.data.NotificationEvent;
import com.ase.notifaction.model.data.NotificationUser;
import com.ase.notifaction.model.notificationcreation.INotificationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationSender {

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

    private void send(NotificationUser user, NotificationEvent event,
            INotificationCreator notificationCreator) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(SEND_NOTIFICATION_ENDPOINT,
                new NotificationContent(user.getEmail(), notificationCreator.create(event)));
    }

    /**
     * Send a notification about an "upcoming" event to the given user
     *
     * @param user  the user who will receive the notification
     * @param event the event about which will be notified
     */
    public void sendUpcoming(NotificationUser user, NotificationEvent event) {
        send(user, event, notificationCreatorUpcoming);
    }

    /**
     * Send a notification about an "updated" event to the given user
     *
     * @param user  the user who will receive the notification
     * @param event the event about which will be notified
     */
    public void sendUpdated(NotificationUser user, NotificationEvent event) {
        send(user, event, notificationCreatorUpdated);
    }
}
