package com.example.ase_project.sendnotification;

import com.example.ase_project.sendnotification.mailsender.AMailSender;
import com.example.ase_project.sendnotification.mailsender.IMailSenderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NotificationSendService {

    // TODO: remove me; How can we delegate this somewhere else
    private static final String SENDER_EMAIL = "my-gmail-mail-adress@gmail.com";
    private static final String SENDER_PASSWORD = "my-gmail-app-password";

    private final AMailSender mailSender;

    @Autowired
    public NotificationSendService(@Qualifier("google") IMailSenderFactory mailSenderFactory) {
        this.mailSender = mailSenderFactory.create(SENDER_EMAIL, SENDER_PASSWORD);
    }

    public void sendNotification(String email, String message) {
        mailSender.send(email, message);
    }

}
