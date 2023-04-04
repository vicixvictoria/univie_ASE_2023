package com.example.ase_project.sendnotification;

import com.example.ase_project.sendnotification.mailsender.AMailSender;
import com.example.ase_project.sendnotification.mailsender.IMailSenderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.concurrent.Callable;

import static org.mockito.Mockito.when;

public class NotificationSendServiceTest {

    private IMailSenderFactory mailSenderFactory;
    private AMailSender mailSender;

    @BeforeEach
    public void setupMailSenderFactory() {
        mailSenderFactory = Mockito.mock(IMailSenderFactory.class);
        mailSender = Mockito.mock(AMailSender.class);
        when(mailSenderFactory.create(Mockito.anyString(), Mockito.anyString())).thenReturn(mailSender);
    }

    @Test
    public void NotificationSendService_constructor_factoryCalled() {
        NotificationSendService notificationSendService = new NotificationSendService(mailSenderFactory);
        Mockito.verify(mailSenderFactory, Mockito.times(1)).create(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void NotificationSendService_sendNotification_sendCalled() {
        NotificationSendService notificationSendService = new NotificationSendService(mailSenderFactory);
        String email = "test@mail.com";
        String message = "Example text";
        notificationSendService.sendNotification(email, message);
        Mockito.verify(mailSender, Mockito.times(1)).send(email, message);
    }

    @Test
    public void NotificationSendService_sendNotification_invalidEmail() {
        NotificationSendService notificationSendService = new NotificationSendService(mailSenderFactory);
        String email = "123";
        String message = "Example text";
        Executable test = () -> notificationSendService.sendNotification(email, message);
        Assertions.assertThrows(RuntimeException.class, test); // TODO: potentially change to concrete exception
        Mockito.verify(mailSender, Mockito.times(0)).send(email, message);
    }

    @Test
    public void NotificationSendService_sendNotification_emptyMessage() {
        NotificationSendService notificationSendService = new NotificationSendService(mailSenderFactory);
        String email = "test@mail.com";
        String message = "";
        notificationSendService.sendNotification(email, message);
        Mockito.verify(mailSender, Mockito.times(1)).send(email, message);
    }

    @Test
    public void NotificationSendService_sendNotification_emptyEmail() {
        NotificationSendService notificationSendService = new NotificationSendService(mailSenderFactory);
        String email = "";
        String message = "Example text";
        Executable test = () -> notificationSendService.sendNotification(email, message);
        Assertions.assertThrows(RuntimeException.class, test); // TODO: potentially change to concrete exception
        Mockito.verify(mailSender, Mockito.times(0)).send(email, message);
    }


}
