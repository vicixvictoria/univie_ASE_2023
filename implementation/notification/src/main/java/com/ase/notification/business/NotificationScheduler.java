package com.ase.notification.business;

import com.ase.notification.domain.NotificationEvent;
import com.ase.notification.business.notificationsendtime.INotificationSendTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class NotificationScheduler {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final NotificationSender notificationSender;
    private final INotificationSendTime notificationSendTime;

    private final Map<NotificationEvent, ScheduledFuture<?>> futureStore = new HashMap<>();

    @Autowired
    public NotificationScheduler(ThreadPoolTaskScheduler taskScheduler,
            NotificationSender notificationSender,
            @Qualifier("day-before") INotificationSendTime notificationSendTime) {
        this.taskScheduler = taskScheduler;
        this.notificationSender = notificationSender;
        this.notificationSendTime = notificationSendTime;

        // This is used when an event gets updated, to cancel the scheduled notification
        this.taskScheduler.setRemoveOnCancelPolicy(true);
    }

    /**
     * Schedule the given notification to be sent at a later point in time
     *
     * @param event the to be scheduled notification
     */
    public void schedule(NotificationEvent event) {
        var future = taskScheduler.schedule(
                () -> notificationSender.sendUpcoming(event), notificationSendTime.get(event));

        futureStore.put(event, future);
    }

    /**
     * Cancel the scheduled notification.
     *
     * @param event the event about which would have been notified.
     */
    public void cancelScheduled(NotificationEvent event) {
        if (!futureStore.containsKey(event)) {
            // fail gracefully
            assert false;
            return;
        }

        var oldFuture = futureStore.get(event);
        assert oldFuture != null;

        oldFuture.cancel(false);
        futureStore.remove(event);
    }

}
