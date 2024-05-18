package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<WeatherResponse> callExternalWeatherApi(String lat, String lng) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

        try {
            return restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    WeatherResponse.class
            );
        }
        catch (HttpClientErrorException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            if (statusCode.equals(UNAUTHORIZED)) {
                return ResponseEntity.status(401).body(null);
            } else if (statusCode.equals(NOT_FOUND)) {
                return ResponseEntity.status(404).body(null);
            } else if (statusCode.equals(TOO_MANY_REQUESTS)) {
                return ResponseEntity.status(429).body(null);
            }
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
