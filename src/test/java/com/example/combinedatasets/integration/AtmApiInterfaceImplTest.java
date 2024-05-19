package com.example.combinedatasets.integration;

import com.example.combinedatasets.domain.AtmResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class AtmApiInterfaceImplTest {

    @Autowired
    private AtmApiInterfaceImpl atmApiInterface;

    @Test
    void callSporitelnaAtmsList() {
        ResponseEntity<AtmResponse> response = atmApiInterface.callSporitelnaAtmsList();
        System.out.println("API Response: " + response.getBody());
    }
}