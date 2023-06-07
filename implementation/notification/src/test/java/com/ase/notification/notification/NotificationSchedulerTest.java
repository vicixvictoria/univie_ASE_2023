package com.ase.notification.notification;

import static org.mockito.Mockito.when;

import com.ase.notification.business.NotificationScheduler;
import com.ase.notification.business.NotificationSender;
import com.ase.notification.domain.NotificationEvent;
import com.ase.notification.business.notificationsendtime.INotificationSendTime;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class NotificationSchedulerTest {

    private ThreadPoolTaskScheduler taskScheduler;
    private NotificationSender notificationSender;
    private INotificationSendTime notificationSendTime;
    private Instant returnedInstant;

    private NotificationScheduler notificationScheduler;

    @BeforeEach
    public void setNotificationScheduler() {
        returnedInstant = Instant.now();

        taskScheduler = Mockito.mock(ThreadPoolTaskScheduler.class);
        when(taskScheduler.schedule(Mockito.any(), Mockito.eq(returnedInstant)))
                .thenReturn(Mockito.mock(ScheduledFuture.class));
        notificationSender = Mockito.mock(NotificationSender.class);
        notificationSendTime = Mockito.mock(INotificationSendTime.class);
        when(notificationSendTime.get(Mockito.any())).thenReturn(returnedInstant);

        notificationScheduler = new NotificationScheduler(taskScheduler, notificationSender,
                notificationSendTime);
    }

    @Test
    public void NotificationScheduler_schedule_validCall() {
        NotificationEvent event = Mockito.mock(NotificationEvent.class);

        notificationScheduler.schedule(event);
        Mockito.verify(taskScheduler, Mockito.times(1))
                .schedule(Mockito.any(), Mockito.eq(returnedInstant));
    }

    @Test
    public void NotificationScheduler_cancelSchedule_validCall() {
        NotificationEvent event = Mockito.mock(NotificationEvent.class);

        // first store the event
        notificationScheduler.schedule(event);
        Mockito.verify(taskScheduler, Mockito.times(1))
                .schedule(Mockito.any(), Mockito.eq(returnedInstant));

        notificationScheduler.cancelScheduled(event);
    }

    @Test
    public void NotificationScheduler_cancelSchedule_notContained_shouldThrow() {
        NotificationEvent event = Mockito.mock(NotificationEvent.class);

        Executable exec = () -> notificationScheduler.cancelScheduled(event);
        Assertions.assertThrows(AssertionError.class, exec);
    }

}
