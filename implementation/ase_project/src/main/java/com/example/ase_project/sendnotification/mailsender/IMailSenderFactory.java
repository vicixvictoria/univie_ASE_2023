package com.example.ase_project.sendnotification.mailsender;

public interface IMailSenderFactory {
    AMailSender create(String senderEmail, String senderPassword);
}
