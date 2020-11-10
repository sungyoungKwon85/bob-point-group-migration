package com.gc.bob.mig.point.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gc.bob.mig.point.entity.Restaurant;
import com.gc.bob.mig.point.model.NaverReverseGeocodeResponse;
import com.gc.bob.mig.point.model.NaverReverseGeocodeResponse.Center;
import com.gc.bob.mig.point.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantUmdMigrationService {

    private final NaverCloudPlatformService naverCloudPlatformService;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void test_migrateRestaurantUmd() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = restaurantRepository.findById("5f97c7d9fefa7831e5dbcdd2").orElse(null);
        if (restaurant != null) {
            restaurants.add(restaurant);
            migrate(restaurants);
        }
    }


    @Transactional
    public void migrateRestaurantUmd() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        migrate(restaurants);
    }

    private void migrate(List<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getLocation() != null
                && restaurant.getLocation().getCoordinates() != null
                && restaurant.getLocation().getCoordinates().size() == 2) {

                List<Double> coordinates = restaurant.getLocation().getCoordinates();
                NaverReverseGeocodeResponse reverseGeocode =
                    naverCloudPlatformService.reverseGeocode(coordinates.get(0), coordinates.get(1));

                if (reverseGeocode != null && reverseGeocode.getStatus().getCode() == 0) {
                    restaurant.getAddress().setUmd(reverseGeocode.getResults().get(0).getRegion().getArea3().getName());
                }
            }
        }

        restaurantRepository.saveAll(restaurants);
    }

}
