package com.example.ase_project.notification.model;

import com.example.ase_project.notification.model.data.EEventType;
import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import com.example.ase_project.notification.model.notificationsendtime.ENotificationConstants;
import com.example.ase_project.notification.model.notificationsendtime.INotificationSendTime;
import com.example.ase_project.notification.model.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
public class NotificationService {

    private final INotificationRepository repository;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final NotificationSender notificationSender;
    private final INotificationSendTime notificationSendTime;

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

        // TODO: remove this. this is just for testing :)
//        NotificationEvent event = new NotificationEvent("ev_id", "event_name", "such description",
//                Date.from(Instant.now().plusSeconds(ENotificationConstants.DAY_SECONDS.get()).plusSeconds(30)),
//                EEventType.REGISTERED);
//        this.addEvent("epic_id", event);
//        this.addEvent("lame_id", event);
    }

    private void scheduleNotification(NotificationUser user, NotificationEvent event) {
        // TODO: We will need the future to be able to call `future.cancel(false)` to be able to cancel scheduled
        //  notifications. Thus we will need to save it somewhere
        var future = taskScheduler.schedule(() -> notificationSender.sendUpcoming(user, event),
                notificationSendTime.get(event));
    }

    public Collection<NotificationEvent> getEvents(String userId) {
        return repository.getEvents(userId);
    }

    public void addEvent(String userId, NotificationEvent event) {
        NotificationUser user = repository.getUserById(userId);
        scheduleNotification(user, event);
        repository.addEvent(userId, event);
    }

    public void updateEvent(NotificationEvent event) {
        Collection<NotificationUser> updatedUsers = repository.updateEvent(event);

        // TODO: remove old taskScheduler

        for (var user : updatedUsers) {
            scheduleNotification(user, event);
            notificationSender.sendUpdated(user, event);
        }
    }

    public Collection<NotificationUser> getUser() {
        return repository.getUser();
    }
}
