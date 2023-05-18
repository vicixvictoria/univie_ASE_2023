package com.ase.sendNotification;

import com.ase.sendNotification.mailsender.AMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationSendService {

    private final AMailSender mailSender;
    @Autowired
    public NotificationSendService(@Qualifier("google") AMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a notification with the message to the given email
     * @param userId the userId of the recipient of the notification
     * @param message the message of the notification
     */
    public void sendNotification(String userId, String message) {
        mailSender.send(userId, message);
    }

}
