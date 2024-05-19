package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherApiInterfaceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherApiInterfaceImpl weatherApiInterfaceImpl;

    @Value("${apiUrlWeather}")
    private String apiUrlWeather;

    @Value("${api.key}")
    private String apiKey;

    private final String lat = "50.044595";
    private final String lng = "14.331565";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherApiInterfaceImpl = new WeatherApiInterfaceImpl(apiUrlWeather, apiKey, restTemplate);
    }

    @Test
    public void testCallExternalWeatherApi_Success() throws IOException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(apiUrlWeather)
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        File jsonFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("WeatherApiResponse.json")).getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponse mockResponse = objectMapper.readValue(jsonFile, WeatherResponse.class);

        ResponseEntity<WeatherResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq(uriBuilder.toUriString()), eq(HttpMethod.GET), any(), eq(WeatherResponse.class)))
                .thenReturn(responseEntity);

        ResponseEntity<WeatherResponse> response = weatherApiInterfaceImpl.callExternalWeatherApi(lat, lng);

        assertEquals(response, responseEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testCallExternalWeatherApi_Unauthorized() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(apiUrlWeather)
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        String uriString = uriBuilder.toUriString();

        when(restTemplate.exchange(eq(uriString), eq(HttpMethod.GET), any(), eq(WeatherResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        ResponseEntity<WeatherResponse> response = weatherApiInterfaceImpl.callExternalWeatherApi(lat, lng);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCallExternalWeatherApi_NotFound() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(apiUrlWeather)
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        String uriString = uriBuilder.toUriString();

        when(restTemplate.exchange(eq(uriString), eq(HttpMethod.GET), any(), eq(WeatherResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseEntity<WeatherResponse> response = weatherApiInterfaceImpl.callExternalWeatherApi(lat, lng);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}