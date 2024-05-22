package com.kodilla.airporthater.externalApi.client;

import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.externalApi.config.AirportApiConfig;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
@Builder
public class AirportApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportApiClient.class);

    private final RestTemplate restTemplate;
    private final AirportApiConfig airportApiConfig;

    public AirportApiClient(RestTemplate restTemplate, AirportApiConfig airportApiConfig) {
        this.restTemplate = restTemplate;
        this.airportApiConfig = airportApiConfig;
    }

    private URI buildAirportUri(String iataCode) {
        return UriComponentsBuilder.fromHttpUrl(airportApiConfig.getAirportApiEndpoint())
                .path("/airports/iata/")
                .path(iataCode)
                .build().toUri();
    }

    public Airport fetchAirportData(String iataCode) {
        URI uri = buildAirportUri(iataCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", airportApiConfig.getAirportApiKey());
        headers.set("X-RapidAPI-Host", airportApiConfig.getAirportApiHost());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Map<String,Object>>() {});
        Map<String, Object> responseBody = responseEntity.getBody();

        return Airport.builder()
                .iataCode((String) responseBody.get("iata_code"))
                .icaoCode((String) responseBody.get("icao_code"))
                .name((String) responseBody.get("name"))
                .city((String) responseBody.get("city"))
                .build();
    }
}
