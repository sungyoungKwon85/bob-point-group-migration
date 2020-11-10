package com.gc.bob.mig.point.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.gc.bob.mig.point.entity.AdministrativeArea;
import com.gc.bob.mig.point.entity.Location;
import com.gc.bob.mig.point.entity.Restaurant;
import com.gc.bob.mig.point.repository.AdministrativeAreaRepository;
import com.gc.bob.mig.point.repository.PointRepository;
import com.gc.bob.mig.point.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointGroupService {

    private final AdministrativeAreaRepository administrativeAreaRepository;
    private final RestaurantRepository restaurantRepository;
    private final PointRepository pointRepository;

    public void makePointGroup() {
        List<AdministrativeArea> sggs = administrativeAreaRepository.findByType("sgg");
        List<AdministrativeArea> umds = administrativeAreaRepository.findByType("umd");
        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<com.gc.bob.mig.point.entity.Point> points = new ArrayList<>();
        for (AdministrativeArea sgg : sggs) {
            List<Restaurant> restaurantsBySgg = restaurants.stream()
                .filter(r -> StringUtils.equals(r.getAddress().getSgg(), sgg.getName()))
                .collect(Collectors.toList());

            Map<Box, List<Restaurant>> twoByTwo = getBoxMapWith(getBoxFor(restaurantsBySgg), 2.0, restaurantsBySgg);
            points.addAll(getPointsWith(sgg, twoByTwo, 12));

            Map<Box, List<Restaurant>> fourByFour = getBoxMapWith(getBoxFor(restaurantsBySgg), 4.0, restaurantsBySgg);
            points.addAll(getPointsWith(sgg, fourByFour, 13));
        }

        for (AdministrativeArea umd : umds) {
            List<Restaurant> restaurantsByUmd = restaurants.stream()
                .filter(r -> StringUtils.equals(r.getAddress().getUmd(), umd.getName()))
                .collect(Collectors.toList());

            Map<Box, List<Restaurant>> two = getBoxMapWith(getBoxFor(restaurantsByUmd), 2.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, two, 14));

            Map<Box, List<Restaurant>> four = getBoxMapWith(getBoxFor(restaurantsByUmd), 4.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, four, 15));

            Map<Box, List<Restaurant>> six = getBoxMapWith(getBoxFor(restaurantsByUmd), 6.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, six, 16));

            Map<Box, List<Restaurant>> eight = getBoxMapWith(getBoxFor(restaurantsByUmd), 8.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, eight, 17));

            Map<Box, List<Restaurant>> ten = getBoxMapWith(getBoxFor(restaurantsByUmd), 10.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, ten, 18));

            Map<Box, List<Restaurant>> twelve = getBoxMapWith(getBoxFor(restaurantsByUmd), 12.0, restaurantsByUmd);
            points.addAll(getPointsWith(umd, twelve, 19));
        }
        pointRepository.saveAll(points);
    }

    private List<com.gc.bob.mig.point.entity.Point> getPointsWith(
        AdministrativeArea area, Map<Box, List<Restaurant>> twoByTwo, int z) {
        return twoByTwo.values().stream()
            .filter(e -> e.size() > 0)
            .map(byZone -> {
                double xx = 0.0, yy = 0.0;
                List<String> ids = new ArrayList<>();
                for (Restaurant r : byZone) {
                    xx += r.getLocation().getCoordinates().get(0);
                    yy += r.getLocation().getCoordinates().get(1);
                    ids.add(r.getId());
                }
                xx = xx / (double) byZone.size();
                yy = yy / (double) byZone.size();
                String parentAreaName = "";
                String parentAreaId = "";
                switch (area.getType()) {
                    case "umd":
                        parentAreaName = area.getSggName();
                        parentAreaId = area.getSggId();
                        break;
                    case "sgg":
                        parentAreaName = area.getSdName();
                        parentAreaId = area.getSdId();
                        break;
                }

                return com.gc.bob.mig.point.entity.Point.builder()
                    .location(Location.builder().type("Point").coordinates(Arrays.asList(xx, yy)).build())
                    .areaName(area.getName())
                    .areaId(area.getId())
                    .parentAreaId(parentAreaId)
                    .parentAreaName(parentAreaName)
                    .ids(ids)
                    .z(z)
                    .count(byZone.size())
                    .build();
            }).collect(Collectors.toList());
    }

    private Map<Box, List<Restaurant>> getBoxMapWith(Box box, double unit, List<Restaurant> restaurants) {
        Map<Box, List<Restaurant>> zoneMap = new HashMap<>();
        Point bl = box.getFirst();
        Point tr = box.getSecond();
        double horizontal = (tr.getX() - bl.getX()) / unit;
        double vertical = (tr.getY() - bl.getY()) / unit;
        for (int i = 1; i < unit + 1; i++) {
            for (int j = 1; j < unit + 1; j++) {
                double lon1 = bl.getX() + horizontal * (j - 1);
                double lat1 = bl.getY() + vertical * (i - 1);
                double lon2 = bl.getX() + horizontal * j;
                double lat2 = bl.getY() + vertical * i;
                zoneMap.put(new Box(new Point(lon1, lat1), new Point(lon2, lat2)), new ArrayList<>());
            }
        }

        for (Restaurant restaurant : restaurants) {
            double x = restaurant.getLocation().getCoordinates().get(0);
            double y = restaurant.getLocation().getCoordinates().get(1);
            zoneMap = zoneMap.entrySet().stream()
                .map(e -> {
                    Point bl2 = e.getKey().getFirst();
                    Point tr2 = e.getKey().getSecond();
                    if (isPointInBox(new Point(x, y), new Box(bl2, tr2))) {
                        e.getValue().add(restaurant);
                    }
                    return e;
                })
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        }

        return zoneMap;
    }

    private Box getBoxFor(List<Restaurant> restaurants) {
        double left = 200.0;
        double bottom = 200.0;
        double right = 0.0;
        double top = 0.0;
        for (Restaurant restaurant : restaurants) {
            double x = restaurant.getLocation().getCoordinates().get(0);
            double y = restaurant.getLocation().getCoordinates().get(1);
            if (x <= left) {
                left = x;
            }
            if (y <= bottom) {
                bottom = y;
            }
            if (x > right) {
                right = x;
            }
            if (y > top) {
                top = y;
            }
        }
        return new Box(new Point(left, bottom), new Point(right, top));
    }

    private boolean isPointInBox(Point p1, Box box) {
        return box.getFirst().getX() <= p1.getX() && box.getFirst().getY() <= p1.getY()
            && box.getSecond().getX() >= p1.getX() && box.getSecond().getY() >= p1.getY();
    }
}
