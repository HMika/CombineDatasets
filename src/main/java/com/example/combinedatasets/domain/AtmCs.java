package com.example.combinedatasets.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
public class AtmCs {
    private Long id;
    private Location location;
    private String type;
    private String state;
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

    @Data
    @Builder
    public static class Location {
        private double lat;
        private double lng;
    }
}
