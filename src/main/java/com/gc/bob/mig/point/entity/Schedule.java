package com.gc.bob.mig.point.entity;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Schedule {
    private LocalTime weekdayStartAt;
    private LocalTime weekdayEndAt;
    private LocalTime saturdayStartAt;
    private LocalTime saturdayEndAt;
    private LocalTime holidayStartAt;
    private LocalTime holidayEndAt;
    private LocalTime deliveryStartAt;
    private LocalTime deliveryEndAt;
}