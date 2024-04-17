package com.it120p.carehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private boolean sendCustomMail(String sendToEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendToEmail);
        message.setSubject(subject);
        message.setText(body);

        try { mailSender.send(message); }
        catch (Exception ex) { return false; }
        return true;
    }

    public boolean sendVerificationMail(String targetEmail, String verificationString) {

        String verificationMessage = String.format(
                """
                This is your verification message.
                The verification code is %s
                """,
                verificationString
        );

        return sendCustomMail(
                targetEmail,
                "Email Verification",
                verificationMessage
        );
    }
}
