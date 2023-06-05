package com.ase.sendNotification;

import static org.mockito.Mockito.when;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.sendNotification.NotificationContent;
import com.ase.common.user.EUserRole;
import com.ase.common.user.User;
import java.io.Serializable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

    private User getUserMock() {
        User user = Mockito.mock(User.class);
        when(user.id()).thenReturn("sampleid");
        when(user.email()).thenReturn("sample@email.com");
        when(user.role()).thenReturn(EUserRole.ATTENDEE);

        return user;
    }

    private <T extends Serializable> RabbitMQMessage<T> wrapInRabbitMQ(T content,
            EMessageType messageType) {
        RabbitMQMessage<T> message = Mockito.mock(RabbitMQMessage.class);
        when(message.getContent()).thenReturn(content);
        when(message.getMessageType()).thenReturn(messageType);

        return message;
    }

    private <T extends Serializable> RabbitMQMessage<T> wrapInRabbitMQ(T content) {
        return wrapInRabbitMQ(content, EMessageType.NEW);
    }

    @Test
    public void Subscriber_sendNotification_validCall() {
        NotificationContent notificationContent = getNotificationContentMock();
        RabbitMQMessage<NotificationContent> message = wrapInRabbitMQ(notificationContent);
        subscriber.sendNotification(message);
        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .sendNotification(notificationContent.userId(), notificationContent.message());
    }

    @Test
    public void Subscriber_userConsumer_NEW_validCall() {
        User mockUser = getUserMock();
        RabbitMQMessage<User> message = wrapInRabbitMQ(mockUser);

        subscriber.userConsumer(message);

        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .newUser(ArgumentMatchers.argThat(notificationUser ->
                        notificationUser.getEmail().equals(mockUser.email())
                                && notificationUser.getId().equals(mockUser.id())));
    }

    @Test
    public void Subscriber_userConsumer_UPDATE_validCall() {
        User mockUser = getUserMock();
        RabbitMQMessage<User> message = wrapInRabbitMQ(mockUser, EMessageType.UPDATE);

        subscriber.userConsumer(message);

        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .updateUser(ArgumentMatchers.argThat(notificationUser ->
                        notificationUser.getEmail().equals(mockUser.email())
                                && notificationUser.getId().equals(mockUser.id())));
    }

    @Test
    public void Subscriber_userConsumer_DELETE_validCall() {
        User mockUser = getUserMock();
        RabbitMQMessage<User> message = wrapInRabbitMQ(mockUser, EMessageType.DELETE);

        subscriber.userConsumer(message);

        Mockito.verify(notificationSendServiceMock, Mockito.times(1))
                .deleteUser(ArgumentMatchers.argThat(notificationUser ->
                        notificationUser.getEmail().equals(mockUser.email())
                                && notificationUser.getId().equals(mockUser.id())));
    }

}
