package com.example.combinedatasets.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
public class CombinedResponse extends AtmCs {
    private String temperature;
    private String humidity;
}
