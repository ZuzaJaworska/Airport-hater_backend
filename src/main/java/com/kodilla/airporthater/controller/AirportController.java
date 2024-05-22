package com.kodilla.airporthater.controller;

import com.kodilla.airporthater.domain.dto.AirportDto;
import com.kodilla.airporthater.domain.dto.WeatherResponseDto;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.exception.exceptions.AirportNotFoundException;
import com.kodilla.airporthater.exception.exceptions.FailedToCreateAirportException;
import com.kodilla.airporthater.externalApi.client.AirportApiClient;
import com.kodilla.airporthater.externalApi.client.WeatherApiClient;
import com.kodilla.airporthater.mapper.AirportMapper;
import com.kodilla.airporthater.scheduler.EmailScheduler;
import com.kodilla.airporthater.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airport")
@CrossOrigin(origins = "*")
public class AirportController {

    private static final Logger log = LoggerFactory.getLogger(AirportController.class);
    private final AirportMapper airportMapper;
    private final AirportService airportService;
    private final AirportApiClient airportApiClient;
    private final WeatherApiClient weatherApiClient;
    private final EmailScheduler emailScheduler;

    // GET
    @GetMapping
    public ResponseEntity<List<AirportDto>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airportMapper.mapToAirportDtoList(airports));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<AirportDto> getAirportByCity(@PathVariable("city") String city)
            throws AirportNotFoundException {
        return ResponseEntity.ok(airportMapper.mapToAirportDto(airportService.getAirportByCity(city)));
    }

    @GetMapping("/iata/{iataCode}")
    public ResponseEntity<AirportDto> getAirportByIataCode(@PathVariable("iataCode") String iataCode)
            throws AirportNotFoundException {
        return ResponseEntity.ok(airportMapper.mapToAirportDto(airportService.getAirportByIataCode(iataCode)));
    }

    @GetMapping("/icao/{icaoCode}")
    public ResponseEntity<AirportDto> getAirportByIcaoCode(@PathVariable("icaoCode") String icaoCode)
            throws AirportNotFoundException {
        return ResponseEntity.ok(airportMapper.mapToAirportDto(airportService.getAirportByIcaoCode(icaoCode)));
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAirport(@RequestBody AirportDto airportDto) throws FailedToCreateAirportException {
        try {
            Airport airport = airportMapper.mapToAirport(airportDto);
            airportService.createAirport(airport);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new FailedToCreateAirportException();
        }
    }

    // PUT
    @PutMapping
    public ResponseEntity<AirportDto> updateAirport(@RequestBody AirportDto airportDto)
            throws AirportNotFoundException, FailedToCreateAirportException {
        Airport airport = airportMapper.mapToAirport(airportDto);
        Airport updatedAirport = airportService.createAirport(airport);
        return ResponseEntity.ok().body(airportMapper.mapToAirportDto(updatedAirport));
    }

    // DELETE
    @DeleteMapping("/{iataCode}")
    public ResponseEntity<Void> deleteAirport(@PathVariable("iataCode") String iataCode) throws AirportNotFoundException {
        airportService.deleteAirport(iataCode);
        return ResponseEntity.ok().build();
    }

    //------------------------------------------------------------------------------------------------------------

    // from external Airport API
    @GetMapping("/searchNewAirport/{iataCode}")
    public ResponseEntity<AirportDto> searchNewAirportInExternalApi(@PathVariable("iataCode") String iataCode) {
        Airport airport = airportApiClient.fetchAirportData(iataCode);
        AirportDto airportDto = airportMapper.mapToAirportDto(airport);
        return ResponseEntity.ok().body(airportDto);
    }

    @PostMapping("/saveNewAirport/{iataCode}")
    public ResponseEntity<Void> saveNewAirportFromExternalApi(@PathVariable("iataCode") String iataCode) {
        Airport existingAirport = null;
        try {
            existingAirport = airportService.getAirportByIataCode(iataCode);
        } catch (AirportNotFoundException e) {
            // Airport with given IATA code doesn't exist in db
        }

        if (existingAirport != null) {
            log.info("Airport with IATA code {} already exists", iataCode);
            return ResponseEntity.badRequest().build();
        }

        Airport airport = airportApiClient.fetchAirportData(iataCode);
        try {
            airportService.createAirport(airport);
            emailScheduler.sendNewAirportEmail(iataCode);
        } catch (FailedToCreateAirportException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    // from external Weather API
    @GetMapping("/weather/{iataCode}")
    public ResponseEntity<WeatherResponseDto> getAirportWeatherFromExternalApi(@PathVariable("iataCode") String iataCode) {
        WeatherResponseDto weatherResponse = weatherApiClient.fetchWeatherData(iataCode);
        if (weatherResponse != null) {
            return ResponseEntity.ok(weatherResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
