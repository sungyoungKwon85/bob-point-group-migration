package com.gc.bob.mig.point.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Document(collation = "point")
@Getter
@Builder
public class Point {
    @Id
    private String id;
    @Indexed
    private Integer z;
    @Indexed
    private String areaName;
    @Indexed
    private String areaId;
    @Indexed
    private String parentAreaName;
    @Indexed
    private String parentAreaId;
    private Integer count;
    @Indexed
    private Location location;
    private List<String> ids;
}
