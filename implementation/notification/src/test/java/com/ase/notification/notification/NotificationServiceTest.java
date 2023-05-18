package com.ase.notification.notification;

import static org.mockito.Mockito.when;

import com.ase.notification.model.NotificationSender;
import com.ase.notification.model.NotificationService;
import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.notificationsendtime.INotificationSendTime;
import com.ase.notification.model.repository.IEventRepository;
import com.ase.notification.model.repository.INotificationEventRepository;
import java.time.Instant;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class NotificationServiceTest {

    // TODO: finish later. The signatures of the methods might still change
    private INotificationEventRepository notificationEventRepository;
    private IEventRepository eventRepository;
    private Collection<NotificationEvent> returnedEvents;
    private ThreadPoolTaskScheduler taskScheduler;
    private NotificationSender notificationSender;
    private INotificationSendTime notificationSendTime;
    private Instant returnedInstant;

    private NotificationService notificationService;

    @BeforeEach
    public void setNotificationService() {
        notificationEventRepository = Mockito.mock(INotificationEventRepository.class);
        eventRepository = Mockito.mock(IEventRepository.class);

//        returnedEvents = Mockito.mock(Collection.class);
//        when(repository.getEvents(Mockito.anyString())).thenReturn(returnedEvents);

        notificationService = new NotificationService(notificationEventRepository, eventRepository,
                taskScheduler, notificationSender, notificationSendTime);
    }

    @BeforeEach
    public void setTaskScheduler() {
        taskScheduler = Mockito.mock(ThreadPoolTaskScheduler.class);
    }

    @BeforeEach
    public void setNotificationSender() {
        notificationSender = Mockito.mock(NotificationSender.class);
    }
//
//    @BeforeEach
//    public void setNotificationSendTime() {
//        notificationSendTime = Mockito.mock(INotificationSendTime.class);
//        returnedInstant = Mockito.mock(Instant.class);
//        when(notificationSendTime.get(Mockito.any())).thenReturn(returnedInstant);
//    }
//
//    @BeforeEach
//    public void createNotificationService() {
//
//    }
//
//    @Test
//    public void NotificationService_addEvent_validCall() {
//
//        NotificationUser user = new NotificationUser();
//        user.setId("someId");
//        NotificationEvent event = Mockito.mock(NotificationEvent.class);
//
//        sendService.addEvent(user.getId(), event);
//
//        Mockito.verify(repository, Mockito.times(1)).addEvent(user, event);
//        Mockito.verify(taskScheduler, Mockito.times(1))
//                .schedule(Mockito.any(), Mockito.eq(returnedInstant));
//    }
//
//    @Test
//    @Disabled
//    public void NotificationService_addEvent_emptyId() {
//        NotificationService sendService = new NotificationService(repository, taskScheduler,
//                notificationSender, notificationSendTime);
//        String userId = "";
//        NotificationEvent event = Mockito.mock(NotificationEvent.class);
//
//        Executable test = () -> sendService.addEvent(userId, event);
//
//        Assertions.assertThrows(RuntimeException.class, test);
//        Mockito.verify(taskScheduler, Mockito.times(0))
//                .schedule(Mockito.any(), Mockito.any(Instant.class));
//    }
//
//    @Test
//    public void NotificationService_getEvents_validCall() {
//        NotificationService sendService = new NotificationService(repository, taskScheduler,
//                notificationSender, notificationSendTime);
//        String userId = "someId";
//        var result = sendService.getEvents(userId);
//
//        Assertions.assertEquals(returnedEvents, result);
//        Mockito.verify(taskScheduler, Mockito.times(0))
//                .schedule(Mockito.any(), Mockito.any(Instant.class));
//        Mockito.verify(repository, Mockito.times(1)).getEvents(userId);
//    }
//
//    @Test
//    @Disabled
//    public void NotificationService_getEvents_emptyId() {
//        NotificationService sendService = new NotificationService(repository, taskScheduler,
//                notificationSender, notificationSendTime);
//        String userId = "";
//        Executable test = () -> sendService.getEvents(userId);
//
//        Assertions.assertThrows(RuntimeException.class, test);
//        Mockito.verify(taskScheduler, Mockito.times(0))
//                .schedule(Mockito.any(), Mockito.any(Instant.class));
//        Mockito.verify(repository, Mockito.times(0)).getEvents(userId);
//    }
//
//    @Test
//    public void NotificationService_updateEvent_validCall() {
//        NotificationService sendService = new NotificationService(repository, taskScheduler,
//                notificationSender, notificationSendTime);
//        NotificationEvent event = Mockito.mock(NotificationEvent.class);
//        sendService.updateEvent(event);
//
//        Mockito.verify(repository, Mockito.times(1)).updateEvent(event);
//        Mockito.verify(taskScheduler, Mockito.times(updatedUser.size()))
//                .schedule(Mockito.any(), Mockito.eq(returnedInstant));
//        Mockito.verify(notificationSender, Mockito.times(updatedUser.size()))
//                .sendUpdated(Mockito.any(), Mockito.eq(event));
//    }
    // TODO: maybe also add invalid case

//    @Test
//    public void NotificationService_getUser_validCall() {
//        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
//        var result = sendService.getUser();
//
//        Assertions.assertEquals(returnedUser, result);
//        Mockito.verify(taskScheduler, Mockito.times(0)).schedule(Mockito.any(), Mockito.any(Instant.class));
//        Mockito.verify(repository, Mockito.times(1)).getUser();
//    }
}
