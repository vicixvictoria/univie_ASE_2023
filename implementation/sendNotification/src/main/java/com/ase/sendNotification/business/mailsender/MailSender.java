package com.ase.sendNotification.business.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    private final JavaMailSender mailSender;

    @Autowired
    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email to targetEmail with the given message
     *
     * @param targetEmail the address which will receive an email
     * @param message     the content of the email
     */
    public void send(String targetEmail, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(targetEmail);
        mailMessage.setSubject("Notification!");
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
