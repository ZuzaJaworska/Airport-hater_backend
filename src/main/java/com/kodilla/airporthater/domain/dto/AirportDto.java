package com.kodilla.airporthater.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportDto {

    private String iataCode;
    private String icaoCode;
    private String name;
    private String city;
    private double airportScore;
    @Builder.Default
    private List<Long> commentIds  = new ArrayList<>();

}
