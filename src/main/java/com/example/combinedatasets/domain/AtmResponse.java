package com.example.combinedatasets.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtmResponse {
    private int pageNumber;
    private int pageCount;
    private int pageSize;
    private int totalItemCount;
    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
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
        private String installDate;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Location {
            private double lat;
            private double lng;
        }
    }
}
