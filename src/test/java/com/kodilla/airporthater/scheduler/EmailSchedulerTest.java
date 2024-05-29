package com.kodilla.airporthater.scheduler;

import com.kodilla.airporthater.config.AdminConfig;
import com.kodilla.airporthater.domain.Email;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.repository.UserRepository;
import com.kodilla.airporthater.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private AirportScoreAvgRepository airportScoreAvgRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Test
    void shouldSendMondayInformationEmail() {
        // Given
        when(userRepository.count()).thenReturn(2L);
        when(airportRepository.count()).thenReturn(3L);
        when(commentRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");

        // When
        emailScheduler.sendMondayInformationEmail();

        // Then
        verify(emailService, times(1)).send(any(Email.class));
    }

    @Test
    void shouldSendLowScoreAirportEmail() {
        // Given
        String iataCode = "WAW";
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        when(airportRepository.findByIataCode(iataCode)).thenReturn(Optional.of(
                Airport.builder().name("Chopin Airport").city("Warsaw").build()
        ));
        when(airportScoreAvgRepository.findByIataCode(iataCode)).thenReturn(Optional.of(
                AirportScoreAvg.builder().scoreAvg(2.5).build()
        ));

        // When
        emailScheduler.sendLowScoreAirportEmail(iataCode);

        // Then
        verify(emailService, times(1)).send(any(Email.class));
    }

    @Test
    void shouldSendNewAirportEmail() {
        // Given
        String iataCode = "WAW";
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        when(airportRepository.findByIataCode(iataCode)).thenReturn(Optional.of(
                Airport.builder().name("Chopin Airport").city("Warsaw").icaoCode("EPWA").build()
        ));

        // When
        emailScheduler.sendNewAirportEmail(iataCode);

        // Then
        verify(emailService, times(1)).send(any(Email.class));
    }
}
