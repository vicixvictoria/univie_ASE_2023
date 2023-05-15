package com.ase.notifaction.model.notificationcreation;


import com.ase.notifaction.model.data.NotificationEvent;

public interface INotificationCreator {

    /**
     * Creates a notification message given an event
     *
     * @param event the event from which the notification message will be generated
     * @return the notification message
     */
    String create(NotificationEvent event);
}
