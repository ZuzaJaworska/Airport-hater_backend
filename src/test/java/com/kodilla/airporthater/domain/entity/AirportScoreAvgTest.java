package com.kodilla.airporthater.domain.entity;

import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    }

//
//class AirportScoreAvgControllerTest {
//
//    private AirportScoreAvgMapper airportScoreAvgMapper;
//    private AirportScoreAvgService airportScoreAvgService;
//    private AirportScoreAvgController airportScoreAvgController;
//
//    @BeforeEach
//    void setUp() {
//        airportScoreAvgMapper = new AirportScoreAvgMapper();
//        airportScoreAvgService = new AirportScoreAvgService() {
//            @Override
//            public List<AirportScoreAvg> getAllAirportsScores() {
//                return Arrays.asList(
//                        new AirportScoreAvg("AAA", 4.5),
//                        new AirportScoreAvg("BBB", 3.8)
//                );
//            }
//
//            @Override
//            public AirportScoreAvg getAirportScoreAvgByIataCode(String iataCode) throws AirportScoreNotFoundException {
//                return new AirportScoreAvg("CCC", 4.2);
//            }
//        };
//        airportScoreAvgController = new AirportScoreAvgController(airportScoreAvgMapper, airportScoreAvgService);
//    }
//
//    @Test
//    void testGetAllAirportsScoresAvg() {
//        // when
//        ResponseEntity<List<AirportScoreAvgDto>> responseEntity = airportScoreAvgController.getAllAirportsScoresAvg();
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(2, responseEntity.getBody().size());
//    }
//
//    @Test
//    void testGetAirportScoreAvgByIataCode() throws AirportScoreNotFoundException {
//        // when
//        ResponseEntity<AirportScoreAvgDto> responseEntity = airportScoreAvgController.getAirportScoreAvgByIataCode("CCC");
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("CCC", responseEntity.getBody().getIataCode());
//        assertEquals(4.2, responseEntity.getBody().getScoreAvg());
//    }
//}

}