package com.example.combinedatasets.service;

import com.example.combinedatasets.domain.AtmCs;
import com.example.combinedatasets.domain.CombinedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
class CombineDataServiceTest {

    @Autowired
    private CombineDataService combineDataService;

    @Autowired
    private DeserializeAtmService deserializeAtmService;

    String mockResponse = """
            {
                "pageNumber": 0,
                "pageCount": 1,
                "pageSize": 311,
                "totalItemCount": 311,
                "items": [
                    {
                        "id": 225,
                        "location": {
                            "lat": 50.089688,
                            "lng": 14.42289
                        },
                        "type": "ATM",
                        "state": "OPEN",
                        "name": "Česká spořitelna, a.s.",
                        "address": "Dlouhá 743/9",
                        "city": "Praha 1",
                        "postCode": "11000",
                        "country": "CZ",
                        "region": "Hlavní město Praha",
                        "bankCode": "0800",
                        "accessType": "nepřetržitě",
                        "atmNumber": "225",
                        "cityPart": "Staré Město",
                        "installDate": "1996-09-01T00:00+02:00"
                    },
                    {
                                "id": 432,
                                "location": {
                                    "lat": 50.089688,
                                    "lng": 14.42289
                                },
                                "type": "ATM",
                                "state": "OPEN",
                                "name": "Česká spořitelna, a.s.",
                                "address": "Dlouhá 743/9",
                                "city": "Praha 1",
                                "postCode": "11000",
                                "country": "CZ",
                                "region": "Hlavní město Praha",
                                "bankCode": "0800",
                                "accessType": "nepřetržitě",
                                "atmNumber": "432",
                                "cityPart": "Staré Město",
                                "installDate": "2018-07-09T00:00+02:00"
                            },
                            {
                                "id": 180,
                                "location": {
                                    "lat": 50.088343,
                                    "lng": 14.432813
                                },
                                "type": "ATM",
                                "state": "OPEN",
                                "name": "METRO nám. Republiky (B)",
                                "address": "Havlíčkova 1028/5",
                                "city": "Praha 1",
                                "postCode": "11000",
                                "country": "CZ",
                                "region": "Hlavní město Praha",
                                "bankCode": "0800",
                                "accessType": "nepřetržitě",
                                "atmNumber": "180",
                                "cityPart": "Nové Město",
                                "installDate": "1994-08-01T00:00+02:00"
                            }
                ]
            }""";

    @Test
    void conbineDatasets() throws JsonProcessingException {
        ResponseEntity<String> response = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        List<AtmCs> atms = deserializeAtmService.deserializeAtmData(response);

        List<CombinedResponse> combinedResponses = combineDataService.combineDatasets(atms);

        for (CombinedResponse cr : combinedResponses) {
            System.out.println(cr.toString());
        }

    }



}