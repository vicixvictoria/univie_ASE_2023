package com.ase.sendNotification;

import static org.mockito.Mockito.when;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.sendNotification.NotificationContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SubscriberTests {

    //TODO: redo these tests

    private NotificationSendService notificationSendServiceMock;
    private Subscriber subscriber;

    @BeforeEach
    public void setSubscriber() {
        notificationSendServiceMock = Mockito.mock(NotificationSendService.class);
        subscriber = new Subscriber(notificationSendServiceMock);
    }

    private NotificationContent getNotificationContentMock() {
        NotificationContent mock = Mockito.mock(NotificationContent.class);
        when(mock.userId()).thenReturn("sample@email.com");
        when(mock.message()).thenReturn("sample message");
        return mock;
    }

    private RabbitMQMessage<NotificationContent> wrapInRabbitMQ(
            NotificationContent content) {
        RabbitMQMessage<NotificationContent> message = Mockito.mock(RabbitMQMessage.class);
        when(message.getContent()).thenReturn(content);
        when(message.getMessageType()).thenReturn(EMessageType.NEW);

        return message;
    }

    @Test
    public void Subscriber_sendNotification_validCall() {
        NotificationContent notificationContent = getNotificationContentMock();
        RabbitMQMessage<NotificationContent> message = wrapInRabbitMQ(notificationContent);
        subscriber.sendNotification(message);
        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .sendNotification(notificationContent.userId(), notificationContent.message());
    }

}
