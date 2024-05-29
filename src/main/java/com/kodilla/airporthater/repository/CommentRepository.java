package com.kodilla.airporthater.repository;

import com.kodilla.airporthater.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByUserId(Long userId);

    Optional<List<Comment>> findByAirportIataCode(String iataCode);

    // calculate the average score for comments associated with a specific airport
    @Query("SELECT AVG(c.score) FROM Comment c WHERE c.airport.iataCode = :iataCode")
    double findAverageCommentScoreForAirport(String iataCode);
}
