package com.example.ase_project.notification.model;

import com.example.ase_project.notification.model.data.EEventType;
import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import com.example.ase_project.notification.model.notificationsendtime.ENotificationConstants;
import com.example.ase_project.notification.model.notificationsendtime.INotificationSendTime;
import com.example.ase_project.notification.model.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
public class NotificationService {

    //TODO: remove this once I can get the correct user object into this class
    @Value("${NOTIFICATION_TARGET_EMAIL:advancedsoftwareengineer@gmail.com}")
    private String notificationTargetEmail;

    private final INotificationRepository repository;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final NotificationSender notificationSender;
    private final INotificationSendTime notificationSendTime;

    /**
     * Constructor, for the NotificationService
     * @param repository Autowired, the repository, where objects are stored
     * @param taskScheduler Autowired, used to schedule notifications
     * @param notificationSender Autowired, used to send notifications
     * @param notificationSendTime Autowired, used to determine when to send the reminder notification
     */
    @Autowired
    public NotificationService(@Qualifier("local") INotificationRepository repository,
                               ThreadPoolTaskScheduler taskScheduler,
                               NotificationSender notificationSender,
                               @Qualifier("day-before") INotificationSendTime notificationSendTime) {
        this.repository = repository;
        this.taskScheduler = taskScheduler;
        this.notificationSender = notificationSender;
        this.notificationSendTime = notificationSendTime;

        // This is used when an event gets updated, to cancel the scheduled notification
        this.taskScheduler.setRemoveOnCancelPolicy(true);
    }

    private void scheduleNotification(NotificationUser user, NotificationEvent event) {
        // TODO: We will need the future to be able to call `future.cancel(false)` to be able to cancel scheduled
        //  notifications. Thus we will need to save it somewhere
        var future = taskScheduler.schedule(() -> notificationSender.sendUpcoming(user, event),
                notificationSendTime.get(event));
    }

    /**
     * Gets all events given a userId
     * @param userId the userId which will be queried
     * @return a collection of all events associated with the given userId
     */
    public Collection<NotificationEvent> getEvents(String userId) {
        return repository.getEvents(userId);
    }

    /**
     * Adds a notification for the user with the given ID and the given event
     * @param userId the id of the user who will receive a notification
     * @param event the event about which will be notified
     */
    public void addEvent(String userId, NotificationEvent event) {

        // TODO: the user should be queried from somewhere and not hardcoded here
        NotificationUser user = new NotificationUser();
        user.setId(userId);
        user.setEmail(notificationTargetEmail);

        scheduleNotification(user, event);
        repository.addEvent(user, event);
    }

    /**
     * Updates the given event and sends a notification to all users, who are associated with the event
     * @param event the event to be updated, must have the same Id as the to be updated event
     */
    public void updateEvent(NotificationEvent event) {
        Collection<NotificationUser> updatedUsers = repository.updateEvent(event);

        // TODO: remove old taskScheduler

        for (var user : updatedUsers) {
            scheduleNotification(user, event);
            notificationSender.sendUpdated(user, event);
        }
    }
}
