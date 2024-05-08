package com.example.combinedatasets.controller;

import com.example.combinedatasets.domain.CombinedResponse;
import com.example.combinedatasets.service.DataMergeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataMergeController {

    private final DataMergeService dataMergeService;

    public DataMergeController(DataMergeService dataMergeService) {
        this.dataMergeService = dataMergeService;
    }

    @GetMapping("/getCombinedData")
    public ResponseEntity<List<CombinedResponse>> getCombinedData() {
        try {
            List<CombinedResponse> combined = dataMergeService.getCombinedData();
            return ResponseEntity.ok(combined);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
