package com.ase.sendNotification.mailsender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderTestConfiguration {
    @Primary
    @Bean
    public JavaMailSender getJavaMailSender(
            @Value("${sendnotification.google.email:test@email}") String senderEmail,
            @Value("${sendnotification.google.password:testpassword}") String senderPassword) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(2525);
        mailSender.setUsername(senderEmail);
        mailSender.setPassword(senderPassword);

        return mailSender;
    }
}
