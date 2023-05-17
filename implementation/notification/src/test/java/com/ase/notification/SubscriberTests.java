package com.ase.notification;

import com.ase.notification.model.NotificationService;
import com.ase.notification.model.data.NotificationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SubscriberTests {

    private NotificationService notificationServiceMock;
    private Subscriber subscriber;

    @BeforeEach
    public void setSubscriber() {
        notificationServiceMock = Mockito.mock(NotificationService.class);
        subscriber = new Subscriber(notificationServiceMock);
    }

    private NotificationEvent getMockNoticationEvent() {
        return Mockito.mock(NotificationEvent.class);
    }

    @Test
    public void Subscriber_registerNotification_validCall() {
        String userId = "some-id";
        NotificationEvent notificationEvent = getMockNoticationEvent();
        subscriber.registerNotification(userId, notificationEvent);
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .addEvent(userId, notificationEvent);
    }

    @Test
    public void Subscriber_registerNotification_emptyID_validCall() {
        String userId = "";
        NotificationEvent notificationEvent = getMockNoticationEvent();
        subscriber.registerNotification(userId, notificationEvent);
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .addEvent(userId, notificationEvent);
    }

    @Test
    public void Subscriber_eventUpdated_validCall() {
        NotificationEvent notificationEvent = getMockNoticationEvent();
        subscriber.eventUpdated(notificationEvent);
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .updateEvent(notificationEvent);
    }

}
