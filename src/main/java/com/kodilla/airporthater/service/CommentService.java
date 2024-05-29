package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.exception.exceptions.*;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.scheduler.EmailScheduler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AirportService airportService;
    private final AirportScoreAvgRepository airportScoreAvgRepository;

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    private final EmailScheduler emailScheduler;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsForOneUser(Long userId) throws UserNotFoundException {
        return commentRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<Comment> getAllCommentsForOneAirport(String iataCode) throws AirportNotFoundException {
        return commentRepository.findByAirportIataCode(iataCode)
                .orElseThrow(AirportNotFoundException::new);
    }

    public Comment getCommentById(Long id) throws CommentNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
    }

    public Comment createComment(Comment comment) throws FailedToCreateCommentException {
        try {
            Comment savedComment = commentRepository.save(comment);
            // Checks if the save was successful
            if (savedComment == null) {
                throw new FailedToCreateCommentException();
            }
            updateAirportScoreForComment(comment.getAirport().getIataCode());
            return savedComment;
        } catch (Exception e) {
            log.error("Error saving comment: ", e);
            throw new FailedToCreateCommentException();
        }
    }

    //----------------------------------------------------

    // Method to calculate the arithmetic mean of COMMENT_SCORE for a specific airport
    public double calculateAverageCommentScoreForAirport(String iataCode) {
        return commentRepository.findAverageCommentScoreForAirport(iataCode);
    }

    // Method for updating AIRPORT_SCORE based on comments for a specific airport
    void updateAirportScoreForComment(String iataCode) throws AirportScoreNotFoundException {
        double averageCommentScore = calculateAverageCommentScoreForAirport(iataCode);
        AirportScoreAvg airportScoreAvg = airportScoreAvgRepository.findByIataCode(iataCode)
                .orElseThrow(AirportScoreNotFoundException::new);
        airportScoreAvg.updateAirportScoreAvg(averageCommentScore);
        airportScoreAvgRepository.save(airportScoreAvg);
        if (airportScoreAvg.getScoreAvg() < 3.0) {
            emailScheduler.sendLowScoreAirportEmail(iataCode);
        }
    }

    //----------------------------------------------------

    @Transactional
    public void deleteComment(Long id) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        String iataCode = comment.getAirport().getIataCode();
        commentRepository.delete(comment);
        airportService.updateAirportRating(iataCode);
    }
}

