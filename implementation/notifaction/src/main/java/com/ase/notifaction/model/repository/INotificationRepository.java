package com.ase.notifaction.model.repository;

import com.ase.notifaction.model.data.NotificationEvent;
import com.ase.notifaction.model.data.NotificationUser;
import java.util.Collection;

public interface INotificationRepository {

    /**
     * Gets all events associated with the userId
     *
     * @param userId the Id which determines which events will be queried
     * @return a collection of NotifcationEvents which are associated with the given userId
     */
    Collection<NotificationEvent> getEvents(String userId);

    /**
     * Associates an event with a user
     *
     * @param user  the user to be associated
     * @param event the event to be associated
     */
    void addEvent(NotificationUser user, NotificationEvent event);

    /**
     * Updates the given event in the datastore
     *
     * @param event the event to be updated, must have the same Id as the old event
     * @return a collection of users, associated with the updated event
     */
    Collection<NotificationUser> updateEvent(NotificationEvent event);
}

