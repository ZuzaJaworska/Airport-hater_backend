package com.kodilla.airporthater.repository;

import com.kodilla.airporthater.domain.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByCity(String city);

    Optional<Airport> findByIataCode(String iataCode);

    Optional<Airport> findByIcaoCode(String icaoCode);
}
