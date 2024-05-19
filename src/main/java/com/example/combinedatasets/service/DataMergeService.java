package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.AtmResponse;
import com.example.combinedatasets.domain.CombinedResponse;
import com.example.combinedatasets.integration.AtmApiInterfaceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DataMergeService {
    private final AtmApiInterfaceImpl atmApiInterface;
    private final DeserializeAtmService deserializeAtmService;
    private final CombineDataService combineData;

    public DataMergeService(AtmApiInterfaceImpl atmApiInterface, DeserializeAtmService deserializeAtmService, CombineDataService combineData) {
        this.atmApiInterface = atmApiInterface;
        this.deserializeAtmService = deserializeAtmService;
        this.combineData = combineData;
    }

    public List<CombinedResponse> getCombinedData() throws ExecutionException, InterruptedException {
        ResponseEntity<AtmResponse> response = atmApiInterface.callSporitelnaAtmsList();
        List<AtmCs> atms = deserializeAtmService.deserializeAtmData(response);
        return combineData.combineDatasets(atms);
    }

}
