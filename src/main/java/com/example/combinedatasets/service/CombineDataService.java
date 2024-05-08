package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.CombinedResponse;
import com.example.combinedatasets.integration.WeatherApiInterfaceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombineDataService {
    private final WeatherApiInterfaceImpl weatherApiInterface;

    public CombineDataService(WeatherApiInterfaceImpl weatherApiInterface) {
        this.weatherApiInterface = weatherApiInterface;
    }

    public List<CombinedResponse> combineDatasets(List<AtmCs> atms) throws JsonProcessingException {

        List<CombinedResponse> combinedResponseObjectsList = new ArrayList<>();

        int count = 0;
        int limitter = 6;

        String lat = "";
        String lng = "";

        for (AtmCs atm : atms) {
            if (count >= limitter) break;
            count++;

            lat = atm.getLocation().getLat();
            lng = atm.getLocation().getLng();

            ResponseEntity<String> weatherInfo =  weatherApiInterface.callExternalWeatherApi(lat, lng);

            CombinedResponse combined = CombinedResponse.builder()
                    .id(atm.getId())
                    .location(atm.getLocation())
                    .name(atm.getName())
                    .type(atm.getType())
                    .address(atm.getAddress())
                    .city(atm.getCity())
                    .postCode(atm.getPostCode())
                    .country(atm.getCountry())
                    .region(atm.getRegion())
                    .bankCode(atm.getBankCode())
                    .accessType(atm.getAccessType())
                    .atmNumber(atm.getAtmNumber())
                    .cityPart(atm.getCityPart())
                    .installDate(atm.getInstallDate())
                    .temperature(weatherApiInterface.getAtt(weatherInfo, "temp"))
                    .humidity(weatherApiInterface.getAtt(weatherInfo, "humidity"))
                    .build();

            combinedResponseObjectsList.add(combined);
        }
        return combinedResponseObjectsList;
    }

}
