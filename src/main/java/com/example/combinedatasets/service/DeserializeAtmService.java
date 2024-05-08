package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class DeserializeAtmService {
    public List<AtmCs> deserializeAtmData(ResponseEntity<String> response) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.readTree(response.getBody());

        JsonNode items = rootNode.path("items");

        List<AtmCs> atms = new ArrayList<>();

        if (items.isArray()){
            for (JsonNode item : items){
                JsonNode locationNode = item.path("location");
                Location location = new Location();
                location.setLat(locationNode.path("lat").asText());
                location.setLng(locationNode.path("lng").asText());
                AtmCs atm = AtmCs.builder()
                        .id(item.path("id").asText())
                        .location(location)
                        .name(item.path("name").asText())
                        .address(item.path("address").asText())
                        .city(item.path("city").asText())
                        .type(item.path("type").asText())
                        .postCode(item.path("postCode").asText())
                        .country(item.path("country").asText())
                        .region(item.path("region").asText())
                        .bankCode(item.path("bankCode").asText())
                        .accessType(item.path("accessType").asText())
                        .atmNumber(item.path("atmNumber").asText())
                        .cityPart(item.path("cityPart").asText())
                        .installDate(item.path("installDate").asText().isEmpty() ? null : OffsetDateTime.parse(item.path("installDate").asText()))
                        .build();
                atms.add(atm);
            }
        }
        return atms;
    }
}
