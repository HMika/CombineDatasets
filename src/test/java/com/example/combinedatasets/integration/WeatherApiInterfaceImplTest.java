package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class WeatherApiInterfaceImplTest {

    @Autowired
    private WeatherApiInterfaceImpl weatherApiInterface;

    @Test
    void callExternalWeatherApi() {
        String lat = "40.7128";
        String lon = "-74.0060";
        ResponseEntity<WeatherResponse> response = weatherApiInterface.callExternalWeatherApi(lat, lon);
        System.out.println("API Response: " + response.getBody());
    }
}