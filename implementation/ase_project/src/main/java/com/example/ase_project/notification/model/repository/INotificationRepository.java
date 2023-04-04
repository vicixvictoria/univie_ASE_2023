package com.example.ase_project.notification.model.repository;

import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;

import java.util.Collection;

public interface INotificationRepository {
    Collection<NotificationEvent> getEvents(String userId);

    void addEvent(String userId, NotificationEvent event);

    Collection<NotificationUser> getUser();

    NotificationUser getUserById(String userId);

    Collection<NotificationUser> updateEvent(NotificationEvent event);
}

