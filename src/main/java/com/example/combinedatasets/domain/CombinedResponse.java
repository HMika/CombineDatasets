package com.example.combinedatasets.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombinedResponse extends AtmCs {
    private String temperature;
    private String humidity;
}
