package com.example.combinedatasets.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class AtmApiInterfaceImpl implements AtmApiInterface {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;

    public AtmApiInterfaceImpl(RestTemplate restTemplate, @Value("${api.keyCS}") String apiKey,@Value("${apiUrlCS}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @Override
    public ResponseEntity<String> callSporitelnaAtmsList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("WEB-API-key", apiKey);

        URI url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("types", "ATM")
                .build()
                .toUri();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response;
    }
}
