package com.example.combinedatasets.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
public interface WeatherApiInterface {
    ResponseEntity<String> callExternalWeatherApi(String lat, String lng);
    String getAtt(ResponseEntity<String> response, String param) throws JsonProcessingException;

}
