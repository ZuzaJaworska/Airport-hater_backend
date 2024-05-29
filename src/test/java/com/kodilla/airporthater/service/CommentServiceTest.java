package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.exception.exceptions.*;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.scheduler.EmailScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AirportScoreAvgRepository airportScoreAvgRepository;

    @Mock
    private EmailScheduler emailScheduler;

    @InjectMocks
    private CommentService commentService;

    @Test
    void getAllComments() {
        // given
        Comment comment = new Comment();
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        // when
        List<Comment> result = commentService.getAllComments();

        // then
        assertEquals(1, result.size());
    }

    @Test
    void getAllCommentsForOneUser() throws UserNotFoundException {
        // given
        Long userId = 1L;
        when(commentRepository.findByUserId(userId)).thenReturn(Optional.of(Collections.emptyList()));

        // when
        List<Comment> result = commentService.getAllCommentsForOneUser(userId);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllCommentsForOneAirport() throws AirportNotFoundException {
        // given
        String iataCode = "ABC";
        when(commentRepository.findByAirportIataCode(iataCode)).thenReturn(Optional.of(Collections.emptyList()));

        // when
        List<Comment> result = commentService.getAllCommentsForOneAirport(iataCode);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCommentById() throws CommentNotFoundException {
        // given
        Long id = 1L;
        Comment comment = new Comment();
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        // when
        Comment result = commentService.getCommentById(id);

        // then
        assertNotNull(result);
    }

    @Test
    void calculateAverageCommentScoreForAirport() {
        // given
        String iataCode = "ABC";
        double expectedAverage = 4.5;
        when(commentRepository.findAverageCommentScoreForAirport(iataCode)).thenReturn(expectedAverage);

        // when
        double result = commentService.calculateAverageCommentScoreForAirport(iataCode);

        // then
        assertEquals(expectedAverage, result);
    }

    @Test
    void updateAirportScoreForComment() throws AirportScoreNotFoundException {
        // given
        String iataCode = "ABC";
        double averageCommentScore = 4.5;
        AirportScoreAvg airportScoreAvg = new AirportScoreAvg();
        when(commentRepository.findAverageCommentScoreForAirport(iataCode)).thenReturn(averageCommentScore);
        when(airportScoreAvgRepository.findByIataCode(iataCode)).thenReturn(Optional.of(airportScoreAvg));

        // when
        commentService.updateAirportScoreForComment(iataCode);

        // then
        verify(airportScoreAvgRepository, times(1)).save(airportScoreAvg);
        verify(emailScheduler, never()).sendLowScoreAirportEmail(iataCode); // You may need to adjust this based on your actual implementation
    }
}
