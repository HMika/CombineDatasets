package com.example.combinedatasets.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CombinedResponse extends AtmCs {
    private String temperature;
    private String humidity;
}
