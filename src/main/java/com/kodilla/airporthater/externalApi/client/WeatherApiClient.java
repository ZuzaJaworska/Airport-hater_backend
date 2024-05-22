package com.kodilla.airporthater.externalApi.client;

import com.kodilla.airporthater.domain.dto.WeatherResponseDto;
import com.kodilla.airporthater.externalApi.config.WeatherApiConfig;
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
public class WeatherApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClient.class);

    private final RestTemplate restTemplate;
    private final WeatherApiConfig weatherApiConfig;

    public WeatherApiClient(RestTemplate restTemplate, WeatherApiConfig weatherApiConfig) {
        this.restTemplate = restTemplate;
        this.weatherApiConfig = weatherApiConfig;
    }

    private URI buildWeatherUri(String iataCode) {
        return UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherApiEndpoint())
                .path("/current.json")
                .queryParam("q", iataCode)
                .build().toUri();
    }

    public WeatherResponseDto fetchWeatherData(String iataCode) {
        URI uri = buildWeatherUri(iataCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", weatherApiConfig.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", weatherApiConfig.getWeatherApiHost());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });
        Map<String, Object> responseBody = responseEntity.getBody();

        LOGGER.info("Weather data response: {}", responseBody);

        // jak zrobię frontend to trzy linijki do wywalenia, bo nie jest potrzebna nazwa miasta w odpowiedzi
        // wtedy trzeba zmienić WeatherResponseDto w klasie dto i tutaj
        if (responseBody != null && responseBody.containsKey("location")) {
            Map<String, Object> location = (Map<String, Object>) responseBody.get("location");
            String city = (String) location.get("region");
//        }
//        if (responseBody != null && responseBody.containsKey("current")) {
            Map<String, Object> currentWeather = (Map<String, Object>) responseBody.get("current");
            Map<String, Object> condition = (Map<String, Object>) currentWeather.get("condition");
            String weather = (String) condition.get("text");
            String temperature = currentWeather.get("temp_c") + "C";
            String pressure = currentWeather.get("pressure_mb") + "hPa";

            return new WeatherResponseDto(city, weather, temperature, pressure);
        }
        return null;
    }
}
