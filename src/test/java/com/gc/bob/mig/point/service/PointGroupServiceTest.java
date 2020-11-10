package com.gc.bob.mig.point.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.GeoModule;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import com.gc.bob.mig.point.entity.Restaurant;
import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.GeometryCollection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointGroupServiceTest {

    @Autowired
    private PointGroupService pointGroupService;

    @Test
    void makePointGroup() {
        pointGroupService.makePointGroup();
    }
}