package com.ase.sendNotification.mailsender;

public abstract class AMailSender {

    protected final String senderEmail;
    protected final String senderPassword;

    public AMailSender(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    /**
     * Sends an email to targetEmail with the given message
     *
     * @param targetEmail the address which will receive an email
     * @param message     the content of the email
     */
    public abstract void send(String targetEmail, String message);

}
