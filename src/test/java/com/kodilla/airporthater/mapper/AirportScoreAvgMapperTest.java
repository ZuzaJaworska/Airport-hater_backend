package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.AirportScoreAvgDto;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportScoreAvgMapperTest {

    @InjectMocks
    private AirportScoreAvgMapper airportScoreAvgMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapToAirportScoreAvgDto() {
        // Given
        AirportScoreAvg airportScoreAvg = AirportScoreAvg.builder()
                .iataCode("TET")
                .scoreAvg(4.5)
                .build();

        // When
        AirportScoreAvgDto airportScoreAvgDto = airportScoreAvgMapper.mapToAirportScoreAvgDto(airportScoreAvg);

        // Then
        assertEquals(airportScoreAvg.getIataCode(), airportScoreAvgDto.getIataCode());
        assertEquals(airportScoreAvg.getScoreAvg(), airportScoreAvgDto.getScoreAvg());
    }

    @Test
    void shouldMapToAirportScoreAvgDtoList() {
        // Given
        AirportScoreAvg airportScoreAvg1 = AirportScoreAvg.builder()
                .iataCode("TES")
                .scoreAvg(4.5)
                .build();
        AirportScoreAvg airportScoreAvg2 = AirportScoreAvg.builder()
                .iataCode("TES")
                .scoreAvg(3.8)
                .build();
        List<AirportScoreAvg> airportsScoresAvg = Arrays.asList(airportScoreAvg1, airportScoreAvg2);

        // When
        List<AirportScoreAvgDto> airportScoreAvgDtos = airportScoreAvgMapper.mapToAirportScoreAvgDtoList(airportsScoresAvg);

        // Then
        assertEquals(airportsScoresAvg.size(), airportScoreAvgDtos.size());
        for (int i = 0; i < airportsScoresAvg.size(); i++) {
            AirportScoreAvgDto airportScoreAvgDto = airportScoreAvgDtos.get(i);
            AirportScoreAvg airportScoreAvg = airportsScoresAvg.get(i);
            assertEquals(airportScoreAvg.getIataCode(), airportScoreAvgDto.getIataCode());
            assertEquals(airportScoreAvg.getScoreAvg(), airportScoreAvgDto.getScoreAvg());
        }
    }

    @Test
    void shouldMapToAirportAvgScore() {
        // Given
        AirportScoreAvgDto airportScoreAvgDto = AirportScoreAvgDto.builder()
                .iataCode("TET")
                .scoreAvg(4.5)
                .build();

        // When
        AirportScoreAvg airportScoreAvg = airportScoreAvgMapper.mapToAirportAvgScore(airportScoreAvgDto);

        // Then
        assertEquals(airportScoreAvgDto.getIataCode(), airportScoreAvg.getIataCode());
        assertEquals(airportScoreAvgDto.getScoreAvg(), airportScoreAvg.getScoreAvg());
    }
}
