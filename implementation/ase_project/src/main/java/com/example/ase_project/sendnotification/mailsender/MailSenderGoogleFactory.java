package com.example.ase_project.sendnotification.mailsender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("google")
public class MailSenderGoogleFactory implements IMailSenderFactory {
    @Override
    public AMailSender create(String senderEmail, String senderPassword) {
        return new MailSenderGoogle(senderEmail, senderPassword);
    }
}
