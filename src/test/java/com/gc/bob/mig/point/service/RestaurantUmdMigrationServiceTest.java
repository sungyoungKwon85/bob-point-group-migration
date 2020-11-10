package com.gc.bob.mig.point.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantUmdMigrationServiceTest {

    @Autowired
    private RestaurantUmdMigrationService restaurantUmdMigrationService;

//    @Test
//    void test_migrateRestaurantUmd() {
//        restaurantUmdMigrationService.migrateRestaurantUmd();
//    }
}