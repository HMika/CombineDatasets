package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.AtmResponse;
import org.springframework.http.ResponseEntity;

public interface AtmApiInterface {
    ResponseEntity<AtmResponse> callSporitelnaAtmsList();

}
