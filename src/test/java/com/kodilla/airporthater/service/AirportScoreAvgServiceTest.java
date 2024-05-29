package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.exception.exceptions.AirportScoreNotFoundException;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class AirportScoreAvgServiceTest {

    @Mock
    private AirportScoreAvgRepository airportScoreAvgRepository;

    @InjectMocks
    private AirportScoreAvgService airportScoreAvgService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllAirportsScores() {
        // Given
        List<AirportScoreAvg> expectedAirportsScores = new ArrayList<>();
        expectedAirportsScores.add(new AirportScoreAvg("AAA", 4.5));
        expectedAirportsScores.add(new AirportScoreAvg("BBB", 3.2));
        when(airportScoreAvgRepository.findAll()).thenReturn(expectedAirportsScores);

        // When
        List<AirportScoreAvg> result = airportScoreAvgService.getAllAirportsScores();

        // Then
        assertEquals(expectedAirportsScores.size(), result.size());
        for (int i = 0; i < expectedAirportsScores.size(); i++) {
            assertEquals(expectedAirportsScores.get(i), result.get(i));
        }
    }

    @Test
    public void shouldGetAirportScoreAvgByIataCode() throws AirportScoreNotFoundException {
        // Given
        String iataCode = "AAA";
        AirportScoreAvg expectedAirportScore = new AirportScoreAvg(iataCode, 4.5);
        when(airportScoreAvgRepository.findByIataCode(iataCode)).thenReturn(Optional.of(expectedAirportScore));

        // When
        AirportScoreAvg result = airportScoreAvgService.getAirportScoreAvgByIataCode(iataCode);

        // Then
        assertEquals(expectedAirportScore, result);
    }

    @Test
    public void shouldThrowAirportScoreNotFoundException() {
        // Given
        String iataCode = "ZZZ";
        when(airportScoreAvgRepository.findByIataCode(iataCode)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AirportScoreNotFoundException.class, () -> {
            airportScoreAvgService.getAirportScoreAvgByIataCode(iataCode);
        });
    }
}
