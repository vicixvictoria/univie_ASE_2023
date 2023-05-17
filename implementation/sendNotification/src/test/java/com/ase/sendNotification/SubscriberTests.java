package com.ase.sendNotification;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SubscriberTests {

    private NotificationSendService notificationSendServiceMock;
    private Subscriber subscriber;

    @BeforeEach
    public void setSubscriber() {
        notificationSendServiceMock = Mockito.mock(NotificationSendService.class);
        subscriber = new Subscriber(notificationSendServiceMock);
    }

    private NotificationContent getNotificationContentMock() {
        NotificationContent mock = Mockito.mock(NotificationContent.class);
        when(mock.email()).thenReturn("sample@email.com");
        when(mock.message()).thenReturn("sample message");
        return mock;
    }

    @Test
    public void Subscriber_sendNotification_validCall() {
        NotificationContent notificationContent = getNotificationContentMock();
        subscriber.sendNotification(notificationContent);
        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .sendNotification(notificationContent.email(), notificationContent.message());
    }

}
