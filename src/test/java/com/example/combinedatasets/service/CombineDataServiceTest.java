package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.AtmResponse;
import com.example.combinedatasets.domain.CombinedResponse;
import com.example.combinedatasets.domain.WeatherResponse;
import com.example.combinedatasets.integration.WeatherApiInterfaceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
class CombineDataServiceTest {
    @Mock
    private WeatherApiInterfaceImpl weatherApiInterfaceImpl;

    private CombineDataService combineDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        combineDataService = new CombineDataService(weatherApiInterfaceImpl);
    }

    @Test
    void testCombineDataService() throws IOException, ExecutionException, InterruptedException {
        File atmFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("atm_response.json")).getFile());
        ObjectMapper objectMapper = new ObjectMapper();
        AtmResponse atmResponse = objectMapper.readValue(atmFile, AtmResponse.class);

        List<AtmCs> atms = atmResponse.getItems().stream().map(item -> AtmCs.builder()
                .id(item.getId())
                .location(AtmCs.Location.builder()
                        .lat(item.getLocation().getLat())
                        .lng(item.getLocation().getLng())
                        .build())
                .type(item.getType())
                .state(item.getState())
                .name(item.getName())
                .address(item.getAddress())
                .city(item.getCity())
                .postCode(item.getPostCode())
                .country(item.getCountry())
                .region(item.getRegion())
                .bankCode(item.getBankCode())
                .accessType(item.getAccessType())
                .atmNumber(item.getAtmNumber())
                .cityPart(item.getCityPart())
                .installDate(OffsetDateTime.parse(item.getInstallDate()))
                .build()).collect(Collectors.toList());

        File weatherFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("weather_response.json")).getFile());
        WeatherResponse weatherResponse = objectMapper.readValue(weatherFile, WeatherResponse.class);

        ResponseEntity<WeatherResponse> weatherResponseResponseEntity = new ResponseEntity<>(weatherResponse, HttpStatus.OK);

        when(weatherApiInterfaceImpl.callExternalWeatherApi(anyString(), anyString()))
                .thenReturn(weatherResponseResponseEntity);

        List<CombinedResponse> combinedResponses = combineDataService.combineDatasets(atms);

        assertNotNull(combinedResponses);
        assertEquals(311, combinedResponses.size());
        CombinedResponse combinedResponse = combinedResponses.get(0);
        assertEquals(225, combinedResponse.getId());
        assertEquals("Česká spořitelna, a.s.", combinedResponse.getName());
        assertEquals("ATM", combinedResponse.getType());
        assertEquals("Dlouhá 743/9", combinedResponse.getAddress());
        assertEquals("Praha 1", combinedResponse.getCity());
        assertEquals("11000", combinedResponse.getPostCode());
        assertEquals("CZ", combinedResponse.getCountry());
        assertEquals("Hlavní město Praha", combinedResponse.getRegion());
        assertEquals("0800", combinedResponse.getBankCode());
        assertEquals("nepřetržitě", combinedResponse.getAccessType());
        assertEquals("225", combinedResponse.getAtmNumber());
        assertEquals("Staré Město", combinedResponse.getCityPart());
        assertEquals("15.95", combinedResponse.getTemperature());
        assertEquals("71", combinedResponse.getHumidity());
        assertEquals(50.089688, combinedResponse.getLocation().getLat());
        assertEquals(14.42289, combinedResponse.getLocation().getLng());
    }
}