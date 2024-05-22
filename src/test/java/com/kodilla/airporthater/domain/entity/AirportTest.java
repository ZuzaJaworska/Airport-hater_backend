package com.kodilla.airporthater.domain.entity;

import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AirportTest {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportScoreAvgRepository scoreAvgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Airport airportOne;
    private Airport airportTwo;

    @AfterEach
    public void tearDown() {
        scoreAvgRepository.deleteAll();
        commentRepository.deleteAll();
        airportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        scoreAvgRepository.deleteAll();
        commentRepository.deleteAll();
        airportRepository.deleteAll();
        userRepository.deleteAll();

        airportOne = Airport.builder()
                .iataCode("WAW")
                .icaoCode("EPWA")
                .name("Chopin Airport")
                .city("Warsaw")
                .build();
        airportTwo = Airport.builder()
                .iataCode("SIN")
                .icaoCode("WSSS")
                .name("Changi Airport")
                .city("Singapore")
                .build();
    }

    @Test
    public void testSaveAirport() {
        // Given

        // When
        airportRepository.save(airportOne);
        Optional<Airport> airportResult = airportRepository.findByIataCode(airportOne.getIataCode());

        // Then
        assertTrue(airportResult.isPresent());
        assertEquals(airportOne.getIataCode(), airportResult.get().getIataCode());
        assertEquals(airportOne.getIcaoCode(), airportResult.get().getIcaoCode());
        assertEquals(airportOne.getName(), airportResult.get().getName());
        assertEquals(airportOne.getCity(), airportResult.get().getCity());
    }

    @Test
    public void testGetAllAirports() {
        // Given
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);

        // When
        List<Airport> allAirports = airportRepository.findAll();

        // Then
        assertEquals(2, allAirports.size());
        assertTrue(allAirports.contains(airportOne));
        assertTrue(allAirports.contains(airportTwo));
    }

    @Test
    public void testFindAirportByIataCode() {
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);

        // When
        Optional<Airport> airportResultOne = airportRepository.findByIataCode(airportOne.getIataCode());
        Optional<Airport> airportResultTwo = airportRepository.findByIataCode(airportTwo.getIataCode());

        // Then
        assertTrue(airportResultOne.isPresent());
        assertTrue(airportResultTwo.isPresent());
        assertEquals(airportOne.getIataCode(), airportResultOne.get().getIataCode());
        assertEquals(airportTwo.getIataCode(), airportResultTwo.get().getIataCode());
        assertEquals(airportOne.getIcaoCode(), airportResultOne.get().getIcaoCode());
        assertEquals(airportTwo.getIcaoCode(), airportResultTwo.get().getIcaoCode());
        assertEquals(airportOne.getName(), airportResultOne.get().getName());
        assertEquals(airportTwo.getName(), airportResultTwo.get().getName());
    }

    @Test
    public void testFindAirportByIcaoCode() {
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);

        // When
        Optional<Airport> airportResultOne = airportRepository.findByIcaoCode(airportOne.getIcaoCode());
        Optional<Airport> airportResultTwo = airportRepository.findByIcaoCode(airportTwo.getIcaoCode());

        // Then
        assertTrue(airportResultOne.isPresent());
        assertTrue(airportResultTwo.isPresent());
        assertEquals(airportOne.getIataCode(), airportResultOne.get().getIataCode());
        assertEquals(airportTwo.getIataCode(), airportResultTwo.get().getIataCode());
        assertEquals(airportOne.getIcaoCode(), airportResultOne.get().getIcaoCode());
        assertEquals(airportTwo.getIcaoCode(), airportResultTwo.get().getIcaoCode());
        assertEquals(airportOne.getName(), airportResultOne.get().getName());
        assertEquals(airportTwo.getName(), airportResultTwo.get().getName());
    }

    @Test
    public void testFindAirportByCity() {
        // Given
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);

        // When
        Optional<Airport> airportResultOne = airportRepository.findByCity(airportOne.getCity());
        Optional<Airport> airportResultTwo = airportRepository.findByCity(airportTwo.getCity());

        // Then
        assertTrue(airportResultOne.isPresent());
        assertTrue(airportResultTwo.isPresent());
        assertEquals(airportOne.getIataCode(), airportResultOne.get().getIataCode());
        assertEquals(airportTwo.getIataCode(), airportResultTwo.get().getIataCode());
        assertEquals(airportOne.getIcaoCode(), airportResultOne.get().getIcaoCode());
        assertEquals(airportTwo.getIcaoCode(), airportResultTwo.get().getIcaoCode());
        assertEquals(airportOne.getName(), airportResultOne.get().getName());
        assertEquals(airportTwo.getName(), airportResultTwo.get().getName());
    }

    @Test
    public void testUpdateAirport() {
        // Given
        airportRepository.save(airportOne);

        // When
        Airport updatedAirport = airportRepository.findByIataCode(airportOne.getIataCode()).get();
        updatedAirport.setName("updatedName");
        updatedAirport.setCity("updatedCity");
        airportRepository.save(updatedAirport);
        Optional<Airport> airportResult = airportRepository.findByIataCode(airportOne.getIataCode());

        // Then
        assertTrue(airportResult.isPresent());
        assertEquals(updatedAirport.getIataCode(), airportResult.get().getIataCode());
        assertEquals(updatedAirport.getName(), airportResult.get().getName());
        assertEquals(updatedAirport.getCity(), airportResult.get().getCity());
    }

    @Test
    public void testDeleteAirport() {
        // Given
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);

        // When
        airportRepository.delete(airportTwo);

        // Then
        assertEquals(1, airportRepository.findAll().size());
        assertTrue(airportRepository.findAll().contains(airportOne));
        assertFalse(airportRepository.findAll().contains(airportTwo));
    }
}