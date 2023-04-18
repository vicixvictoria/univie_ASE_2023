package com.example.ase_project.notification.model.notificationsendtime;

import com.example.ase_project.notification.model.data.NotificationEvent;

import java.time.Instant;

public interface INotificationSendTime {

    /**
     * gets an instant when a reminder about an event is supposed to be sent
     * @param event the event about which should be reminded.
     * @return an instant, when to send out a reminder about the given event
     */
    Instant get(NotificationEvent event);
}
