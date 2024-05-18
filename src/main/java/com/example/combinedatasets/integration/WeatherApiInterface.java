package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.WeatherResponse;
import org.springframework.http.ResponseEntity;
public interface WeatherApiInterface {
    ResponseEntity<WeatherResponse> callExternalWeatherApi(String lat, String lng);

}
