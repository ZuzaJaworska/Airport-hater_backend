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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentTest {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportScoreAvgRepository scoreAvgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User userOne;
    private User userTwo;
    private Airport airportOne;
    private Airport airportTwo;
    private Comment commentOne;
    private Comment commentTwo;

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

        userOne = User.builder()
                .username("usernameOne")
                .password("passwordOne")
                .build();
        userTwo = User.builder()
                .username("usernameTwo")
                .password("passwordTwo")
                .build();
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
        commentOne = Comment.builder()
                .title("titleOne")
                .body("bodyOne")
                .score(5)
                .createdAt(LocalDateTime.now())
                .user(userOne)
                .airport(airportOne)
                .build();
        commentTwo = Comment.builder()
                .title("titleTwo")
                .body("bodyTwo")
                .score(4)
                .createdAt(LocalDateTime.now())
                .user(userTwo)
                .airport(airportTwo)
                .build();
    }

    @Test
    public void testSaveComment() {
        // Given

        // When
        userRepository.save(userOne);
        airportRepository.save(airportOne);
        commentRepository.save(commentOne);
        Optional<Comment> commentResult = commentRepository.findById(commentOne.getId());

        // Then
        assertTrue(commentResult.isPresent());
        assertEquals(commentOne.getId(), commentResult.get().getId());
        assertEquals(commentOne.getTitle(), commentResult.get().getTitle());
        assertEquals(commentOne.getBody(), commentResult.get().getBody());
        assertEquals(commentOne.getScore(), commentResult.get().getScore());
        assertEquals(commentOne.getUser(), commentResult.get().getUser());
        assertEquals(commentOne.getAirport(), commentResult.get().getAirport());
    }

    @Test
    public void testGetAllComments() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        commentRepository.save(commentOne);
        commentRepository.save(commentTwo);

        // When
        List<Comment> allComments = commentRepository.findAll();

        // Then
        assertEquals(2, allComments.size());
        assertTrue(allComments.contains(commentOne));
        assertTrue(allComments.contains(commentTwo));
    }

    @Test
    public void testGetAllCommentsForOneUser() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        commentRepository.save(commentOne);
        commentRepository.save(commentTwo);

        // When
        Optional<List<Comment>> commentsForUserOne = commentRepository.findByUserId(userOne.getId());

        // Then
        assertTrue(commentsForUserOne.isPresent());
        assertEquals(1, commentsForUserOne.get().size());
        assertTrue(commentsForUserOne.get().contains(commentOne));
        assertFalse(commentsForUserOne.get().contains(commentTwo));
    }

    @Test
    public void testGetAllCommentsForOneAirport() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        commentRepository.save(commentOne);
        commentRepository.save(commentTwo);

        // When
        Optional<List<Comment>> commentsForAirportOne = commentRepository.findByAirportIataCode(airportOne.getIataCode());

        // Then
        assertTrue(commentsForAirportOne.isPresent());
        assertEquals(1, commentsForAirportOne.get().size());
        assertTrue(commentsForAirportOne.get().contains(commentOne));
        assertFalse(commentsForAirportOne.get().contains(commentTwo));
    }

    @Test
    public void testGetCommentById() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        commentRepository.save(commentOne);
        commentRepository.save(commentTwo);

        // When
        Optional<Comment> commentResult = commentRepository.findById(commentOne.getId());

        // Then
        assertTrue(commentResult.isPresent());
        assertEquals(commentOne.getId(), commentResult.get().getId());
        assertEquals(commentOne.getTitle(), commentResult.get().getTitle());
        assertEquals(commentOne.getBody(), commentResult.get().getBody());
        assertEquals(commentOne.getScore(), commentResult.get().getScore());
        assertEquals(commentOne.getUser(), commentResult.get().getUser());
        assertEquals(commentOne.getAirport(), commentResult.get().getAirport());
    }

    @Test
    public void testUpdateComment() {
        // Given
        userRepository.save(userOne);
        airportRepository.save(airportOne);
        commentRepository.save(commentOne);

        // When
        Comment updatedComment = commentRepository.findById(commentOne.getId()).get();
        updatedComment.setTitle("updatedTitle");
        updatedComment.setBody("updatedBody");
        updatedComment.setScore(2);
        commentRepository.save(updatedComment);
        Optional<Comment> commentResult = commentRepository.findById(commentOne.getId());

        //Then
        assertTrue(commentResult.isPresent());
        assertEquals(commentOne.getId(), commentResult.get().getId());
        assertEquals(commentOne.getTitle(), commentResult.get().getTitle());
        assertEquals(commentOne.getBody(), commentResult.get().getBody());
        assertEquals(commentOne.getScore(), commentResult.get().getScore());
    }

    @Test
    public void testDeleteComment() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);
        airportRepository.save(airportOne);
        airportRepository.save(airportTwo);
        commentRepository.save(commentOne);
        commentRepository.save(commentTwo);

        // When
        commentRepository.deleteById(commentTwo.getId());

        // Then
        assertEquals(1, commentRepository.findAll().size());
        assertTrue(commentRepository.findAll().contains(commentOne));
        assertFalse(commentRepository.findAll().contains(commentTwo));
    }
}
