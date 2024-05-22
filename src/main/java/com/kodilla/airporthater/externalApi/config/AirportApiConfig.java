package com.kodilla.airporthater.externalApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AirportApiConfig {

    @Value("${airport.api.endpoint.prod}")
    private String airportApiEndpoint;
    @Value("${airport.api.X-RapidAPI-Key}")
    private String airportApiKey;
    @Value("${airport.api.X-RapidAPI-Host}")
    private String airportApiHost;
}
