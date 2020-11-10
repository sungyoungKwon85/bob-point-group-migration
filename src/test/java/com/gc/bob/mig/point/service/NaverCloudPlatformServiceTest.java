package com.gc.bob.mig.point.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gc.bob.mig.point.model.NaverGeocodeResponse;
import com.gc.bob.mig.point.model.NaverReverseGeocodeResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverCloudPlatformServiceTest {

    @Autowired
    NaverCloudPlatformService naverCloudPlatformService;

    @Test
    public void geocode() {
        NaverGeocodeResponse geocode = naverCloudPlatformService.geocode("구로구");
        assertNotNull(geocode);
    }
    @Test
    public void reverseGeocode() {
        NaverReverseGeocodeResponse reverseGeocode = naverCloudPlatformService.reverseGeocode(127.0872612, 37.5902306);
        assertNotNull(reverseGeocode);
    }

}