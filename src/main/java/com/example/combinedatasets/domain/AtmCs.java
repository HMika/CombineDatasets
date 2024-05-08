package com.example.combinedatasets.domain;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class AtmCs {
    private String id;
    private Location location;
    private String type;
    private String name;
    private String address;
    private String city;
    private String postCode;
    private String country;
    private String region;
    private String bankCode;
    private String accessType;
    private String atmNumber;
    private String cityPart;
    private OffsetDateTime installDate;
}
