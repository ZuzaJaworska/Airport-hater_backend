package com.kodilla.airporthater.repository;

import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportScoreAvgRepository extends JpaRepository<AirportScoreAvg, String> {
    Optional<AirportScoreAvg> findByIataCode(String iataCode);
}
