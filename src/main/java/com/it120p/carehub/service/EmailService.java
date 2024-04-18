package com.it120p.carehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    private void sendCustomMail(String receipientEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(receipientEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async
    public void sendVerificationMail(String targetEmail, String verificationString) {

        String verificationMessage = String.format(
                """
                This is your verification message.
                The verification code is %s
                """,
                verificationString
        );

        sendCustomMail(
                targetEmail,
                "Email Verification",
                verificationMessage
        );
    }
}
