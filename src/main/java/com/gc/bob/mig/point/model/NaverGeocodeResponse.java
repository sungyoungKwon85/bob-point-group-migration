package com.gc.bob.mig.point.model;

import java.util.List;

import lombok.Data;

@Data
public class NaverGeocodeResponse {
    private String status;
    private Meta meta;
    private List<Address> addresses;

    @Data
    public static class Address {
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private List<AddressElement> addressElements;
        private String x;
        private String y;
        private Double distance;
    }
    @Data
    private static class AddressElement {
        private List<String> types;
        private String longName;
        private String shortName;
        private String code;
    }
    @Data
    private static class Meta {
        private Integer totalCount;
        private Integer page;
        private Integer count;
    }
}
