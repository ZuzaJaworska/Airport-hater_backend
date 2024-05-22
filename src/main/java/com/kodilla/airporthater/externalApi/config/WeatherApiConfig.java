package com.kodilla.airporthater.externalApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherApiConfig {

    @Value("${weather.api.endpoint.prod}")
    private String weatherApiEndpoint;
    @Value("${weather.api.X-RapidAPI-Key}")
    private String weatherApiKey;
    @Value("${weather.api.X-RapidAPI-Host}")
    private String weatherApiHost;
}
