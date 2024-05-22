package com.kodilla.airporthater.controller;

import com.kodilla.airporthater.domain.dto.AirportScoreAvgDto;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.exception.exceptions.AirportScoreNotFoundException;
import com.kodilla.airporthater.mapper.AirportScoreAvgMapper;
import com.kodilla.airporthater.service.AirportScoreAvgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airport/score")
@CrossOrigin(origins = "*")
public class AirportScoreAvgController {

    private final AirportScoreAvgMapper airportScoreAvgMapper;
    private final AirportScoreAvgService airportScoreAvgService;

    // GET
    @GetMapping
    public ResponseEntity<List<AirportScoreAvgDto>> getAllAirportsScoresAvg() {
        List<AirportScoreAvg> airportsScores = airportScoreAvgService.getAllAirportsScores();
        return ResponseEntity.ok(airportScoreAvgMapper.mapToAirportScoreAvgDtoList(airportsScores));
    }

    @GetMapping("/{iataCode}")
    public ResponseEntity<AirportScoreAvgDto> getAirportScoreAvgByIataCode(@PathVariable("iataCode") String iataCode)
            throws AirportScoreNotFoundException {
        return ResponseEntity.ok(airportScoreAvgMapper
                .mapToAirportScoreAvgDto(airportScoreAvgService.getAirportScoreAvgByIataCode(iataCode)));
    }
}
