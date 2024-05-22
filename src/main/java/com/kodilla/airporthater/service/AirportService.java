package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.exception.exceptions.AirportNotFoundException;
import com.kodilla.airporthater.exception.exceptions.FailedToCreateAirportException;
import com.kodilla.airporthater.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final CommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(AirportService.class);

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport getAirportByCity(String city) throws AirportNotFoundException {
        return airportRepository.findByCity(city)
                .orElseThrow(AirportNotFoundException::new);
    }

    public Airport getAirportByIataCode(String iataCode) throws AirportNotFoundException {
        return airportRepository.findByIataCode(iataCode)
                .orElseThrow(AirportNotFoundException::new);
    }

    public Airport getAirportByIcaoCode(String icaoCode) throws AirportNotFoundException {
        return airportRepository.findByIcaoCode(icaoCode)
                .orElseThrow(AirportNotFoundException::new);
    }

    public Airport createAirport(Airport airport) throws FailedToCreateAirportException {
        try {
            if (airport.getAirportScoreAvg() == null) {
                AirportScoreAvg airportScoreAvg = new AirportScoreAvg(airport.getIataCode(), 0.0);
                airportScoreAvg.setAirport(airport);
                airport.setAirportScoreAvg(airportScoreAvg);
            }
            Airport savedAirport = airportRepository.save(airport);
            log.info("Airport created successfully: {}", savedAirport);
            return savedAirport;
        } catch (Exception e) {
            log.error("Failed to create airport", e);
            throw new FailedToCreateAirportException();
        }
    }

    public void deleteAirport(String iataCode) throws AirportNotFoundException {
        Airport airport = getAirportByIataCode(iataCode);
        commentService.deleteCommentsForAirport(airport.getIataCode());
        airportRepository.delete(airport);
    }
}
