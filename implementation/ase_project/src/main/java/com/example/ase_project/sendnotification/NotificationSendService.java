package com.example.ase_project.sendnotification;

import com.example.ase_project.sendnotification.mailsender.AMailSender;
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
     * @param email the email recipient of the notification
     * @param message the message of the notification
     */
    public void sendNotification(String email, String message) {
        mailSender.send(email, message);
    }

}
