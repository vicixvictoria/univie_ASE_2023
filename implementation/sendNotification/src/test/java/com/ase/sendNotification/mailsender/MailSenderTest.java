package com.ase.sendNotification.mailsender;

import com.ase.sendNotification.rabbitmq.RabbitMQSendNotificationConfiguration;
import com.ase.sendNotification.rabbitmq.RabbitMQUserConfiguration;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties =
        {"sendnotification.google.email=from@localhost",
                "sendnotification.google.password=test-password"})
public class MailSenderTest {

    @MockBean
    private RabbitMQSendNotificationConfiguration sendNotificationConfiguration;

    @MockBean
    private RabbitMQUserConfiguration userConfiguration;

    private GreenMail smtpServer;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void setUp() {
        // Start the GreenMail test SMTP server
        smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp")).withConfiguration(
                GreenMailConfiguration.aConfig().withDisabledAuthentication());
        smtpServer.start();
    }

    @AfterEach
    public void tearDown() {
        // Stop the GreenMail test SMTP server
        smtpServer.stop();
    }

    @Test
    public void testSend() throws MessagingException, IOException {
        // Test data
        String targetEmail = "recipient@example.com";
        String message = "Test message";

        // Send the email using the MailSender
        mailSender.send(targetEmail, message);

        // Wait for the email to be received by the test SMTP server
        smtpServer.waitForIncomingEmail(1);

        // Get the received email
        MimeMessage[] messages = smtpServer.getReceivedMessages();
        Assertions.assertEquals(1, messages.length);

        // Verify the email content
        MimeMessage msg = messages[0];
        //msg.get()
        Assertions.assertEquals(targetEmail, msg.getHeader("To")[0]);
        Assertions.assertEquals("Notification!", msg.getHeader("Subject")[0]);
        Assertions.assertTrue(((String) msg.getContent()).contains(message));
    }

}
