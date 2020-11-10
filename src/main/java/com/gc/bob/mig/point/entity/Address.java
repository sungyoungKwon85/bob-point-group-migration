package com.gc.bob.mig.point.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {
    private String sg;
    private String sgg;
    private String umd;
    private String zipcode;
    private String asRoad;
    private String asArea;

    public void setUmd(String umd) {
        this.umd = umd;
    }
}