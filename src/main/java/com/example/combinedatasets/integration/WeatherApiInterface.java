package com.example.combinedatasets.integration;

import org.springframework.http.ResponseEntity;
public interface WeatherApiInterface {
    ResponseEntity<String> callExternalWeatherApi(String lat, String lng);

}
