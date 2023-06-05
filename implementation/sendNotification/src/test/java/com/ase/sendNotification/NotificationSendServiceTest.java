package com.ase.sendNotification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ase.sendNotification.mailsender.MailSender;
import com.ase.sendNotification.model.NotificationUser;
import com.ase.sendNotification.repository.IUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class NotificationSendServiceTest {

    private MailSender mailSender;
    private IUserRepository userRepository;
    private NotificationUser notificationUser;
    private final String storedId = "storedId";
    private final String storedEmail = "storedEmail@email.com";
    private NotificationSendService notificationSendService;

    @BeforeEach
    public void setNotificationSendService() {
        mailSender = Mockito.mock(MailSender.class);
        userRepository = Mockito.mock(IUserRepository.class);
        notificationUser = Mockito.mock(NotificationUser.class);
        when(notificationUser.getEmail()).thenReturn(storedEmail);
        when(notificationUser.getId()).thenReturn(storedId);
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.findById(storedId)).thenReturn(Optional.of(notificationUser));
        notificationSendService = new NotificationSendService(mailSender, userRepository);
    }

    @Test
    public void NotificationSendService_sendNotification_sendCalled() {
        String userId = storedId;
        String message = "Example text";
        notificationSendService.sendNotification(userId, message);
        Mockito.verify(mailSender, Mockito.times(1)).send(storedEmail, message);
    }

    @Test
    public void NotificationSendService_sendNotification_invalidId() {
        String userId = "exampleId";
        String message = "Example text";
        Executable executable = () -> notificationSendService.sendNotification(userId, message);
        Assertions.assertThrows(AssertionError.class, executable);
        Mockito.verify(mailSender, Mockito.times(0)).send(any(), any());
    }

    @Test
    public void NotificationSendService_sendNotification_emptyMessage() {
        String userId = storedId;
        String message = "";
        notificationSendService.sendNotification(userId, message);
        Mockito.verify(mailSender, Mockito.times(1)).send(storedEmail, message);
    }

    @Test
    public void NotificationSendService_sendNotification_emptyId() {
        String userId = "";
        String message = "Example text";

        Executable executable = () -> notificationSendService.sendNotification(userId, message);
        Assertions.assertThrows(AssertionError.class, executable);
        Mockito.verify(mailSender, Mockito.times(0)).send(any(), any());
    }

    @Test
    public void NotificationSendService_newUser_validCall() {
        notificationSendService.newUser(notificationUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(notificationUser);
    }

    private NotificationUser getMockNotificationUser(String id, String email) {
        NotificationUser user = Mockito.mock(NotificationUser.class);
        when(user.getId()).thenReturn(id);
        when(user.getEmail()).thenReturn(email);
        return user;
    }

    @Test
    public void NotificationSendService_updateUser_validCall() {
        String newEmail = "new@email.com";
        NotificationUser updatedUser = getMockNotificationUser(storedId, newEmail);
        notificationSendService.updateUser(updatedUser);
        Mockito.verify(userRepository, Mockito.times(1)).findById(storedId);
        Mockito.verify(notificationUser, Mockito.times(1)).setEmail(newEmail);
        Mockito.verify(notificationUser, Mockito.times(0)).setId(any());
    }

    @Test
    public void NotificationSendService_updateUser_unsavedUser() {
        String id = "someId";
        String email = "some@email.com";
        NotificationUser updatedUser = getMockNotificationUser(id, email);
        Executable executable = () -> notificationSendService.updateUser(updatedUser);
        Assertions.assertThrows(AssertionError.class, executable);
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verify(notificationUser, Mockito.times(0)).setId(any());
        Mockito.verify(notificationUser, Mockito.times(0)).setEmail(any());
    }

    @Test
    public void NotificationSendService_updateUser_userWithEmptyId() {
        String id = "";
        String email = "some@email.com";
        NotificationUser updatedUser = getMockNotificationUser(id, email);
        Executable executable = () -> notificationSendService.updateUser(updatedUser);
        Assertions.assertThrows(AssertionError.class, executable);
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verify(notificationUser, Mockito.times(0)).setId(any());
        Mockito.verify(notificationUser, Mockito.times(0)).setEmail(any());
    }

    @Test
    public void NotificationSendService_deleteUser_validCall() {
        notificationSendService.deleteUser(notificationUser);
        Mockito.verify(userRepository, Mockito.times(1)).delete(notificationUser);
    }


}

