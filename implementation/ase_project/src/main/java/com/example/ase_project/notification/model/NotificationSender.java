package com.example.ase_project.notification.model;

import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import com.example.ase_project.notification.model.notificationcreation.INotificationCreator;
import com.example.ase_project.sendnotification.NotificationContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationSender {

    // TODO: is it better if this lives here rather then in the Service class?
    //  I am not sure, as this should decrease coupling, but on the other hand it makes it harder to change which
    //  notificationCreator gets used, as now not all dependency injections happen in the Service
    private final INotificationCreator notificationCreatorUpcoming;
    private final INotificationCreator notificationCreatorUpdated;


    private static final String SEND_NOTIFICATION_ENDPOINT = "http://localhost:8080/sendNotification";

    @Autowired
    public NotificationSender(@Qualifier("placeholder") INotificationCreator notificationCreatorUpcoming,
                              @Qualifier("placeholder") INotificationCreator notificationCreatorUpdated) {
        this.notificationCreatorUpcoming = notificationCreatorUpcoming;
        this.notificationCreatorUpdated = notificationCreatorUpdated;
    }

    private void send(NotificationUser user, NotificationEvent event, INotificationCreator notificationCreator) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(SEND_NOTIFICATION_ENDPOINT,
                new NotificationContent(user.getEmail(), notificationCreator.create(event)));
    }

    public void sendUpcoming(NotificationUser user, NotificationEvent event) {
       send(user, event, notificationCreatorUpcoming);
    }

    public void sendUpdated(NotificationUser user, NotificationEvent event) {
        send(user, event, notificationCreatorUpdated);
    }
}
