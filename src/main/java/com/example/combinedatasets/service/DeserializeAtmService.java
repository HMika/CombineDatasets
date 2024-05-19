package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.AtmResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class DeserializeAtmService {
    public List<AtmCs> deserializeAtmData(ResponseEntity<AtmResponse> response){

        return Objects.requireNonNull(response.getBody()).getItems().stream()
                .map(item -> AtmCs.builder()
                        .id(item.getId())
                        .location(AtmCs.Location.builder()
                                .lng(item.getLocation().getLng())
                                .lat(item.getLocation().getLat())
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
                        .installDate(item.getInstallDate() != null ? OffsetDateTime.parse(item.getInstallDate()) : null)                        .build())
                .collect(Collectors.toList());
    }
}
