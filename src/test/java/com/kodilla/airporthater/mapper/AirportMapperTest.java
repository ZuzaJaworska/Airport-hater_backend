package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.AirportDto;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.mapper.AirportMapper;
import com.kodilla.airporthater.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AirportMapperTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private AirportMapper airportMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapToAirportDto() {
        // Given
        Airport airport = Airport.builder()
                .iataCode("TEST")
                .icaoCode("ICAO")
                .name("Test Airport")
                .city("Test City")
                .build();

        // When
        AirportDto airportDto = airportMapper.mapToAirportDto(airport);

        // Then
        assertEquals(airport.getIataCode(), airportDto.getIataCode());
        assertEquals(airport.getIcaoCode(), airportDto.getIcaoCode());
        assertEquals(airport.getName(), airportDto.getName());
        assertEquals(airport.getCity(), airportDto.getCity());
        assertEquals(0.0, airportDto.getAirportScore());
    }

    @Test
    void shouldMapToAirportDtoList() {
        // Given
        List<Airport> airports = new ArrayList<>();
        airports.add(Airport.builder()
                .iataCode("TEST1")
                .icaoCode("ICAO1")
                .name("Test Airport 1")
                .city("Test City 1")
                .build());
        airports.add(Airport.builder()
                .iataCode("TEST2")
                .icaoCode("ICAO2")
                .name("Test Airport 2")
                .city("Test City 2")
                .build());

        // When
        List<AirportDto> airportDtos = airportMapper.mapToAirportDtoList(airports);

        // Then
        assertEquals(airports.size(), airportDtos.size());
        for (int i = 0; i < airports.size(); i++) {
            AirportDto airportDto = airportDtos.get(i);
            Airport airport = airports.get(i);
            assertEquals(airport.getIataCode(), airportDto.getIataCode());
            assertEquals(airport.getIcaoCode(), airportDto.getIcaoCode());
            assertEquals(airport.getName(), airportDto.getName());
            assertEquals(airport.getCity(), airportDto.getCity());
            assertEquals(0.0, airportDto.getAirportScore());
        }
    }

    @Test
    void shouldMapToAirport() {
        // Given
        AirportDto airportDto = AirportDto.builder()
                .iataCode("TEST")
                .icaoCode("ICAO")
                .name("Test Airport")
                .city("Test City")
                .airportScore(4.5)
                .build();

        // When
        Airport airport = airportMapper.mapToAirport(airportDto);

        // Then
        assertEquals(airportDto.getIataCode(), airport.getIataCode());
        assertEquals(airportDto.getIcaoCode(), airport.getIcaoCode());
        assertEquals(airportDto.getName(), airport.getName());
        assertEquals(airportDto.getCity(), airport.getCity());
    }
}
