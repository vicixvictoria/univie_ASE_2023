package com.example.ase_project.notification.model.notificationsendtime;

import com.example.ase_project.notification.model.data.NotificationEvent;

import java.time.Instant;

public interface INotificationSendTime {
    Instant get(NotificationEvent event);
}
