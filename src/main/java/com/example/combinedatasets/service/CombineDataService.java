package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.CombinedResponse;
import com.example.combinedatasets.domain.WeatherResponse;
import com.example.combinedatasets.integration.WeatherApiInterfaceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Service
public class CombineDataService {
    private final WeatherApiInterfaceImpl weatherApiInterface;
    private static final int THREAD_COUNT = 100;

    public CombineDataService(WeatherApiInterfaceImpl weatherApiInterface) {
        this.weatherApiInterface = weatherApiInterface;
    }

    public List<CombinedResponse> combineDatasets(List<AtmCs> atms) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<List<CombinedResponse>>> futures = new ArrayList<>();

        for (int i = 0; i < atms.size(); i += THREAD_COUNT) {
            int finalI = i;
            futures.add(executorService.submit(() -> processAtmBatch(atms.subList(finalI, Math.min(finalI + THREAD_COUNT, atms.size())))));
        }

        executorService.shutdown();

        List<CombinedResponse> combinedResponseObjectsList = new ArrayList<>();
        for (Future<List<CombinedResponse>> future : futures) {
            combinedResponseObjectsList.addAll(future.get());
        }

        return combinedResponseObjectsList;
    }

    private List<CombinedResponse> processAtmBatch(List<AtmCs> atmBatch) {
        List<CombinedResponse> combinedResponses = new ArrayList<>();
        for (AtmCs atm : atmBatch) {
            String lat = atm.getLocation().getLat();
            String lng = atm.getLocation().getLng();

            ResponseEntity<WeatherResponse> weatherInfo = weatherApiInterface.callExternalWeatherApi(lat, lng);

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
                    .temperature(String.valueOf(Objects.requireNonNull(weatherInfo.getBody()).getMain().getTemp()))
                    .humidity(String.valueOf(weatherInfo.getBody().getMain().getHumidity()))
                    .build();

            combinedResponses.add(combined);
        }
        return combinedResponses;
    }

}
