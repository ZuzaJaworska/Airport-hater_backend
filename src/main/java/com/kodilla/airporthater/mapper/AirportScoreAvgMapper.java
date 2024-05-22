package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.AirportScoreAvgDto;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Builder
public class AirportScoreAvgMapper {

    public AirportScoreAvgDto mapToAirportScoreAvgDto(AirportScoreAvg airportScoreAvg) {
        return AirportScoreAvgDto.builder()
                .iataCode(airportScoreAvg.getIataCode())
                .scoreAvg(airportScoreAvg.getScoreAvg())
                .build();
    }

    public List<AirportScoreAvgDto> mapToAirportScoreAvgDtoList(List<AirportScoreAvg> airportsScoresAvg) {
        return airportsScoresAvg.stream()
                .map(this::mapToAirportScoreAvgDto)
                .collect(Collectors.toList());
    }

    public AirportScoreAvg mapToAirportAvgScore(AirportScoreAvgDto airportScoreAvgDto) {
        return AirportScoreAvg.builder()
                .iataCode(airportScoreAvgDto.getIataCode())
                .scoreAvg(airportScoreAvgDto.getScoreAvg())
                .build();
    }
}
