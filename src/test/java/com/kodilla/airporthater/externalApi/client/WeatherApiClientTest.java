package com.kodilla.airporthater.externalApi.client;

import com.kodilla.airporthater.domain.dto.WeatherResponseDto;
import com.kodilla.airporthater.externalApi.config.WeatherApiConfig;
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

class WeatherApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherApiConfig weatherApiConfig;

    @InjectMocks
    private WeatherApiClient weatherApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFetchWeatherData() {
        // given
        String iataCode = "ABC";
        String expectedCity = "Test City";
        String expectedWeather = "Sunny";
        String expectedTemperature = "25C";
        String expectedPressure = "1010hPa";

        URI uri = URI.create("http://example.com/current.json?q=ABC");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "test-api-key");
        headers.set("X-RapidAPI-Host", "test-api-host");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> location = new HashMap<>();
        location.put("region", expectedCity);
        responseBody.put("location", location);
        Map<String, Object> currentWeather = new HashMap<>();
        Map<String, Object> condition = new HashMap<>();
        condition.put("text", expectedWeather);
        currentWeather.put("condition", condition);
        currentWeather.put("temp_c", 25);
        currentWeather.put("pressure_mb", 1010);
        responseBody.put("current", currentWeather);

        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(weatherApiConfig.getWeatherApiEndpoint()).thenReturn("http://example.com");
        when(weatherApiConfig.getWeatherApiKey()).thenReturn("test-api-key");
        when(weatherApiConfig.getWeatherApiHost()).thenReturn("test-api-host");
        when(restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<Map<String,Object>>() {}))
                .thenReturn(responseEntity);

        // when
        WeatherResponseDto weatherResponseDto = weatherApiClient.fetchWeatherData(iataCode);

        // then
        assertEquals(expectedCity, weatherResponseDto.getCity());
        assertEquals(expectedWeather, weatherResponseDto.getWeather());
        assertEquals(expectedTemperature, weatherResponseDto.getTemperature());
        assertEquals(expectedPressure, weatherResponseDto.getPressure());
    }
}
