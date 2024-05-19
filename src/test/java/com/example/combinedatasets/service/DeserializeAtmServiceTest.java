package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.AtmResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class DeserializeAtmServiceTest {

    @Mock
    private ResponseEntity<AtmResponse> responseEntity;

    private DeserializeAtmService deserializeAtmService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deserializeAtmService = new DeserializeAtmService();
    }

    @Test
    void testDeserializeAtmData_Success() throws Exception {

        File mockResponse = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("deserializeAtmsMock.json")).getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        AtmResponse atmResponse = objectMapper.readValue(mockResponse, AtmResponse.class);

        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(atmResponse);

        List<AtmCs> atmCsList = deserializeAtmService.deserializeAtmData(responseEntity);

        assertNotNull(atmCsList);
        assertEquals(3, atmCsList.size());

        assertNotNull(atmCsList);
        assertEquals(3, atmCsList.size());

        AtmCs firstAtm = atmCsList.get(0);
        assertEquals(225, firstAtm.getId());
        assertEquals("Česká spořitelna, a.s.", firstAtm.getName());
        assertEquals("Dlouhá 743/9", firstAtm.getAddress());
        assertEquals("Praha 1", firstAtm.getCity());
        assertEquals("11000", firstAtm.getPostCode());
        assertEquals("CZ", firstAtm.getCountry());
        assertEquals("Hlavní město Praha", firstAtm.getRegion());
        assertEquals("0800", firstAtm.getBankCode());
        assertEquals("nepřetržitě", firstAtm.getAccessType());
        assertEquals("225", firstAtm.getAtmNumber());
        assertEquals("Staré Město", firstAtm.getCityPart());
        assertEquals(OffsetDateTime.parse("1996-09-01T00:00+02:00"), firstAtm.getInstallDate());
        assertEquals(50.089688, firstAtm.getLocation().getLat());
        assertEquals(14.42289, firstAtm.getLocation().getLng());
    }
}