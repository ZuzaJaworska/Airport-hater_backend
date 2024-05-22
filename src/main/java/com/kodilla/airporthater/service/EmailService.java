package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private SimpleMailMessage createEmailMessage(final Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getMailTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessage());
        return simpleMailMessage;
    }

    public void send(final Email email) {
        log.info("Starting email preparation...");

        try {
            SimpleMailMessage emailMessage = createEmailMessage(email);
            javaMailSender.send(emailMessage);
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: {}", e.getMessage(), e);
        }
    }
}
