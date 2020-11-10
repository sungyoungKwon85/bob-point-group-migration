package com.gc.bob.mig.point.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Document(collection = "administrativeArea")
@Getter
@Builder
public class AdministrativeArea {
    @Id
    private String id;
    private String name;
    private String type;
    private String sdId;
    private String sdName;
    private String sggId;
    private String sggName;
    private Location location;

    public void setLocation(Location location) {
        this.location = location;
    }
}
