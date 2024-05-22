package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.exception.exceptions.AirportScoreNotFoundException;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportScoreAvgService {

    private final AirportScoreAvgRepository airportScoreAvgRepository;

    public List<AirportScoreAvg> getAllAirportsScores() {
        return airportScoreAvgRepository.findAll();
    }

    public AirportScoreAvg getAirportScoreAvgByIataCode(String iataCode) throws AirportScoreNotFoundException {
        return airportScoreAvgRepository.findByIataCode(iataCode)
                .orElseThrow(AirportScoreNotFoundException::new);
    }
}
