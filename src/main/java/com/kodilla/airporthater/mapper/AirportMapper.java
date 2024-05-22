package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.AirportDto;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.domain.entity.AirportScoreAvg;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Builder
public class AirportMapper {

    private final CommentRepository commentRepository;

    public AirportDto mapToAirportDto(Airport airport) {
        List<Long> commentsIds = airport.getComments().stream()
                .map(Comment::getId)
                .toList();
        double airportScoreValue = 0;
        if (airport.getAirportScoreAvg() != null) {
            airportScoreValue = airport.getAirportScoreAvg().getScoreAvg();
        }
        return AirportDto.builder()
                .iataCode(airport.getIataCode())
                .icaoCode(airport.getIcaoCode())
                .name(airport.getName())
                .city(airport.getCity())
                .airportScore(airportScoreValue)
                .commentIds(commentsIds)
                .build();
    }

    public List<AirportDto> mapToAirportDtoList(List<Airport> airports) {
        return airports.stream()
                .map(this::mapToAirportDto)
                .toList();
    }

    public Airport mapToAirport(AirportDto airportDto) {
        Airport airport = Airport.builder()
                .iataCode(airportDto.getIataCode())
                .icaoCode(airportDto.getIcaoCode())
                .name(airportDto.getName())
                .city(airportDto.getCity())
                .build();

        if (airportDto.getAirportScore() != 0) {
            AirportScoreAvg airportScore = AirportScoreAvg.builder()
                    .scoreAvg(airportDto.getAirportScore())
                    .build();
            airport.setAirportScoreAvg(airportScore);
        }

        List<Comment> comments = commentRepository.findAllById(airportDto.getCommentIds());
        airport.setComments(comments);

        return airport;
    }
}
