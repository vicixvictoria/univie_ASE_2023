package com.ase.notification.model.notificationcreation;


import com.ase.notification.model.data.NotificationEvent;

public interface INotificationCreator {

    /**
     * Creates a notification message given an event
     *
     * @param event the event from which the notification message will be generated
     * @return the notification message
     */
    String create(NotificationEvent event);
}
