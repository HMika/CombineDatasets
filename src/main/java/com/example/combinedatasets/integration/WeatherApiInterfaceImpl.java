package com.example.combinedatasets.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherApiInterfaceImpl implements WeatherApiInterface {

    private final String url;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public WeatherApiInterfaceImpl(@Value("${apiUrlWeather}")String url, @Value("${api.key}") String apiKey, RestTemplate restTemplate) {
        this.url = url;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> callExternalWeatherApi(String lat, String lng) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );
        return response;

    }

}
