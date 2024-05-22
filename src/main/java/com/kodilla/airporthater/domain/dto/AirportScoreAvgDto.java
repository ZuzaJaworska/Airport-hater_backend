package com.kodilla.airporthater.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportScoreAvgDto {

    private String iataCode;
    @Builder.Default
    private double scoreAvg = 0.0;
}
