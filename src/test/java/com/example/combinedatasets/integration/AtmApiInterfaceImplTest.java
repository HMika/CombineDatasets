package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.AtmResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.Objects;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class AtmApiInterfaceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<HttpEntity<String>> httpEntityCaptor;

    @InjectMocks
    private AtmApiInterfaceImpl atmApiInterfaceImpl;

    @Value("${api.keyCS}")
    private String apiKey;

    @Value("${apiUrlCS}")
    private String apiUrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atmApiInterfaceImpl = new AtmApiInterfaceImpl(restTemplate, apiKey, apiUrl);
    }

    @Test
    void testCallSporitelnaAtmsList_Success() throws Exception {
        URI url = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("types", "ATM")
                .build()
                .toUri();

        File jsonFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("atm_response.json")).getFile());
        ObjectMapper objectMapper = new ObjectMapper();
        AtmResponse mockResponse = objectMapper.readValue(jsonFile, AtmResponse.class);

        ResponseEntity<AtmResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(AtmResponse.class)))
                .thenReturn(responseEntity);

        ResponseEntity<AtmResponse> response = atmApiInterfaceImpl.callSporitelnaAtmsList();

        assertEquals(response, responseEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testCallSporitelnaAtmsList_ServerError() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("types", "ATM")
                .build()
                .toUri();

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(AtmResponse.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<AtmResponse> response = atmApiInterfaceImpl.callSporitelnaAtmsList();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

}