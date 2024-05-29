package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSendEmailSuccessfully() {
        // Given
        Email email = Email.builder()
                .mailTo("recipient@example.com")
                .subject("Test Subject")
                .message("Test Message")
                .build();

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(email.getMailTo());
        expectedMessage.setSubject(email.getSubject());
        expectedMessage.setText(email.getMessage());

        // When
        emailService.send(email);

        // Then
        verify(javaMailSender, times(1)).send(expectedMessage);
    }

    @Test
    public void shouldHandleMailException() {
        // Given
        Email email = Email.builder()
                .mailTo("recipient@example.com")
                .subject("Test Subject")
                .message("Test Message")
                .build();

        doThrow(new MailSendException("Failed to send email")) // Simulate mail sending failure
                .when(javaMailSender)
                .send(any(SimpleMailMessage.class));

        // When
        emailService.send(email);

        // Then: Ensure that no exception is thrown and it's handled gracefully
    }
}
