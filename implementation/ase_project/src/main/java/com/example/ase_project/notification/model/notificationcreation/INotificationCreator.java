package com.example.ase_project.notification.model.notificationcreation;

import com.example.ase_project.notification.model.data.NotificationEvent;

public interface INotificationCreator {
    String create(NotificationEvent event);
}
