package com.kodilla.airporthater.externalApi.client;

import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.externalApi.config.AirportApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AirportApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AirportApiConfig airportApiConfig;

    @InjectMocks
    private AirportApiClient airportApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFetchAirportData() {
        // given
        String iataCode = "ABC";
        String expectedName = "Test Airport";
        String expectedCity = "Test City";

        URI uri = URI.create("http://example.com/airports/iata/ABC");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "test-api-key");
        headers.set("X-RapidAPI-Host", "test-api-host");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("iata_code", iataCode);
        responseBody.put("name", expectedName);
        responseBody.put("city", expectedCity);

        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(airportApiConfig.getAirportApiEndpoint()).thenReturn("http://example.com");
        when(airportApiConfig.getAirportApiKey()).thenReturn("test-api-key");
        when(airportApiConfig.getAirportApiHost()).thenReturn("test-api-host");
        when(restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Map<String,Object>>() {}))
                .thenReturn(responseEntity);

        // when
        Airport airport = airportApiClient.fetchAirportData(iataCode);

        // then
        assertEquals(iataCode, airport.getIataCode());
        assertEquals(expectedName, airport.getName());
        assertEquals(expectedCity, airport.getCity());
    }
}
