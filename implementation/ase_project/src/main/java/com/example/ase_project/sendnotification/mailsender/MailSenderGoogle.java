package com.example.ase_project.sendnotification.mailsender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Qualifier("google")
public class MailSenderGoogle extends AMailSender {

    private final JavaMailSender mailSender;

    private static JavaMailSender configureMailSender(String senderEmail, String senderPassword) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(senderEmail);
        mailSender.setPassword(senderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public MailSenderGoogle(@Value("${NOTIFICATION_SENDER_EMAIL}") String senderEmail,
                            @Value("${NOTIFICATION_SENDER_PASSWORD}") String senderPassword) {
        super(senderEmail, senderPassword);
        this.mailSender = configureMailSender(this.senderEmail, this.senderPassword);
    }

    /**
     * @inheritDoc
     */
    public void send(String targetEmail, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(targetEmail);
        mailMessage.setSubject("Notification!"); // TODO: extract this string
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
