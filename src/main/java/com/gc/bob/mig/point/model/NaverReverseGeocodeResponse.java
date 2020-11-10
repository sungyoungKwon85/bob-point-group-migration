package com.gc.bob.mig.point.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NaverReverseGeocodeResponse {

    @JsonProperty("results")
    private List<Results> results;
    @JsonProperty("status")
    private Status status;

    @Data
    public static class Results {

        @JsonProperty("region")
        private Region region;
        @JsonProperty("code")
        private Code code;
        @JsonProperty("name")
        private String name;
    }

    @Data
    public static class Region {

        @JsonProperty("area4")
        private Area area4;
        @JsonProperty("area3")
        private Area area3;
        @JsonProperty("area2")
        private Area area2;
        @JsonProperty("area1")
        private Area area1;
        @JsonProperty("area0")
        private Area area0;
    }

    @Data
    public static class Area {

        @JsonProperty("coords")
        private Coords coords;
        @JsonProperty("name")
        private String name;
    }

    @Data
    public static class Coords {

        @JsonProperty("center")
        private Center center;
    }
    @Data
    public static class Center {

        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;
        @JsonProperty("crs")
        private String crs;
    }
    @Data
    public static class Code {

        @JsonProperty("mappingId")
        private String mappingid;
        @JsonProperty("type")
        private String type;
        @JsonProperty("id")
        private String id;
    }
    @Data
    public static class Status {

        @JsonProperty("message")
        private String message;
        @JsonProperty("name")
        private String name;
        @JsonProperty("code")
        private int code;
    }
}
