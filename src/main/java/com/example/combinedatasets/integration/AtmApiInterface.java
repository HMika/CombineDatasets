package com.example.combinedatasets.integration;

import org.springframework.http.ResponseEntity;

public interface AtmApiInterface {
    ResponseEntity<String> callSporitelnaAtmsList();

}
