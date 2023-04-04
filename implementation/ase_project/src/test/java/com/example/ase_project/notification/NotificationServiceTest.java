package com.example.ase_project.notification;

import com.example.ase_project.notification.model.NotificationSender;
import com.example.ase_project.notification.model.NotificationService;
import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import com.example.ase_project.notification.model.notificationsendtime.INotificationSendTime;
import com.example.ase_project.notification.model.repository.INotificationRepository;
import com.example.ase_project.sendnotification.NotificationSendService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;

public class NotificationServiceTest {

    private INotificationRepository repository;
    private Collection<NotificationEvent> returnedEvents;
    private Collection<NotificationUser> updatedUser;
    private Collection<NotificationUser> returnedUser;

    private ThreadPoolTaskScheduler taskScheduler;
    private NotificationSender notificationSender;

    private INotificationSendTime notificationSendTime;
    private Instant returnedInstant;

    @BeforeEach
    public void setRepository() {
        repository = Mockito.mock(INotificationRepository.class);

        returnedEvents = Mockito.mock(Collection.class);
        when(repository.getEvents(Mockito.anyString())).thenReturn(returnedEvents);

        updatedUser = List.of(new NotificationUser(), new NotificationUser(), new NotificationUser());
        when(repository.updateEvent(Mockito.any())).thenReturn(updatedUser);

        returnedUser = Mockito.mock(Collection.class);
        when(repository.getUser()).thenReturn(returnedUser);
    }

    @BeforeEach
    public void setTaskScheduler() {
        taskScheduler = Mockito.mock(ThreadPoolTaskScheduler.class);
    }

    @BeforeEach
    public void setNotificationSender() {
        notificationSender = Mockito.mock(NotificationSender.class);
    }

    @BeforeEach
    public void setNotificationSendTime() {
        notificationSendTime = Mockito.mock(INotificationSendTime.class);
        returnedInstant = Mockito.mock(Instant.class);
        when(notificationSendTime.get(Mockito.any())).thenReturn(returnedInstant);
    }

    @Test
    public void NotificationService_addEvent_validCall() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        String userId = "someId";
        NotificationEvent event = Mockito.mock(NotificationEvent.class);

        sendService.addEvent(userId, event);

        Mockito.verify(repository, Mockito.times(1)).addEvent(userId, event);
        Mockito.verify(taskScheduler, Mockito.times(1)).schedule(Mockito.any(), Mockito.eq(returnedInstant));
    }

    @Test
    public void NotificationService_addEvent_emptyId() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        String userId = "";
        NotificationEvent event = Mockito.mock(NotificationEvent.class);

        Executable test = () -> sendService.addEvent(userId, event);

        Assertions.assertThrows(RuntimeException.class, test);
        Mockito.verify(taskScheduler, Mockito.times(0)).schedule(Mockito.any(), Mockito.any(Instant.class));
    }

    @Test
    public void NotificationService_getEvents_validCall() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        String userId = "someId";
        var result = sendService.getEvents(userId);

        Assertions.assertEquals(returnedEvents, result);
        Mockito.verify(taskScheduler, Mockito.times(0)).schedule(Mockito.any(), Mockito.any(Instant.class));
        Mockito.verify(repository, Mockito.times(1)).getEvents(userId);
    }

    @Test
    public void NotificationService_getEvents_emptyId() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        String userId = "";
        Executable test = () -> sendService.getEvents(userId);

        Assertions.assertThrows(RuntimeException.class, test);
        Mockito.verify(taskScheduler, Mockito.times(0)).schedule(Mockito.any(), Mockito.any(Instant.class));
        Mockito.verify(repository, Mockito.times(0)).getEvents(userId);
    }

    @Test
    public void NotificationService_updateEvent_validCall() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        NotificationEvent event = Mockito.mock(NotificationEvent.class);
        sendService.updateEvent(event);

        Mockito.verify(repository, Mockito.times(1)).updateEvent(event);
        Mockito.verify(taskScheduler, Mockito.times(updatedUser.size())).schedule(Mockito.any(), Mockito.eq(returnedInstant));
        Mockito.verify(notificationSender, Mockito.times(updatedUser.size())).sendUpdated(Mockito.any(), Mockito.eq(event));
    }
    // TODO: maybe also add invalid case

    @Test
    public void NotificationService_getUser_validCall() {
        NotificationService sendService = new NotificationService(repository, taskScheduler, notificationSender, notificationSendTime);
        var result = sendService.getUser();

        Assertions.assertEquals(returnedUser, result);
        Mockito.verify(taskScheduler, Mockito.times(0)).schedule(Mockito.any(), Mockito.any(Instant.class));
        Mockito.verify(repository, Mockito.times(1)).getUser();
    }
}
