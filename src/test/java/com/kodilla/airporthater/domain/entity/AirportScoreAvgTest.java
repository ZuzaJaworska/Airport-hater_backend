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
class AirportScoreAvgTest {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportScoreAvgRepository scoreAvgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Airport airportOne;
    private AirportScoreAvg airportScoreAvg;

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

        airportScoreAvg = AirportScoreAvg.builder()
                .iataCode("WAW")
                .scoreAvg(4.5)
                .build();

        airportOne.setAirportScoreAvg(airportScoreAvg);
        airportScoreAvg.setAirport(airportOne);
    }

    @Test
    public void testSaveAirportScoreAvg() {
        // Given

        // When
        airportRepository.save(airportOne);
        scoreAvgRepository.save(airportScoreAvg);
        Optional<AirportScoreAvg> scoreAvgResult = scoreAvgRepository.findByIataCode(airportScoreAvg.getIataCode());

        // Then
        assertTrue(scoreAvgResult.isPresent());
        assertEquals(airportScoreAvg.getIataCode(), scoreAvgResult.get().getIataCode());
        assertEquals(airportScoreAvg.getScoreAvg(), scoreAvgResult.get().getScoreAvg());
        assertEquals(airportScoreAvg.getAirport().getIataCode(), scoreAvgResult.get().getAirport().getIataCode());
    }

    @Test
    public void testGetAllAirportScoresAvg() {
        // Given
        Airport airportTwo = Airport.builder()
                .iataCode("SIN")
                .icaoCode("WSSS")
                .name("Changi Airport")
                .city("Singapore")
                .build();

        AirportScoreAvg airportScoreAvgTwo = AirportScoreAvg.builder()
                .iataCode("SIN")
                .scoreAvg(4.8)
                .build();

        airportTwo.setAirportScoreAvg(airportScoreAvgTwo);
        airportScoreAvgTwo.setAirport(airportTwo);

        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        scoreAvgRepository.save(airportScoreAvg);
        scoreAvgRepository.save(airportScoreAvgTwo);

        // When
        List<AirportScoreAvg> allScores = scoreAvgRepository.findAll();

        // Then
        assertEquals(2, allScores.size());
        assertTrue(allScores.contains(airportScoreAvg));
        assertTrue(allScores.contains(airportScoreAvgTwo));
    }

    @Test
    public void testDeleteAirportScoreAvg() {
        // Given
        airportRepository.save(airportOne);
        scoreAvgRepository.save(airportScoreAvg);

        // When
        scoreAvgRepository.deleteById(airportScoreAvg.getIataCode());

        // Then
        assertFalse(scoreAvgRepository.findById(airportScoreAvg.getIataCode()).isPresent());
    }

    @Test
    public void testUpdateAirportScoreAvg() {
        // Given
        airportRepository.save(airportOne);
        scoreAvgRepository.save(airportScoreAvg);

        // When
        AirportScoreAvg updatedScoreAvg = scoreAvgRepository.findByIataCode(airportScoreAvg.getIataCode()).get();
        updatedScoreAvg.updateAirportScoreAvg(4.9);
        scoreAvgRepository.save(updatedScoreAvg);
        Optional<AirportScoreAvg> scoreAvgResult = scoreAvgRepository.findByIataCode(airportScoreAvg.getIataCode());

        // Then
        assertTrue(scoreAvgResult.isPresent());
        assertEquals(updatedScoreAvg.getScoreAvg(), scoreAvgResult.get().getScoreAvg());
    }
}
