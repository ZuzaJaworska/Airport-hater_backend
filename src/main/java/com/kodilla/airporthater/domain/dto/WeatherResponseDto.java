package com.kodilla.airporthater.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDto {

    private String city;
    private String weather;
    private String temperature;
    private String pressure;
}
