package com.gc.bob.mig.point;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.gc.bob.mig.point.service.AdministrativeCenterMigrationService;
import com.gc.bob.mig.point.service.PointGroupService;
import com.gc.bob.mig.point.service.RestaurantUmdMigrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigExecutor {

    private final AdministrativeCenterMigrationService administrativeCenterMigrationService;
    private final RestaurantUmdMigrationService restaurantUmdMigrationService;
    private final PointGroupService pointGroupService;

//    @PostConstruct
    public void load() {
        log.debug("==========MigExecutor start===========");
        log.debug("==========AdministrativeArea's Center start===========");
        administrativeCenterMigrationService.migrateCenterOfAdministrativeArea();
        administrativeCenterMigrationService.migrateCenterOfAdministrativeArea_missing();
        log.debug("==========AdministrativeArea's Center end===========");

        log.debug("==========restaurant's umd start===========");
        restaurantUmdMigrationService.migrateRestaurantUmd();
        log.debug("==========restaurant's umd end===========");

        log.debug("==========point group start===========");
        pointGroupService.makePointGroup();
        log.debug("==========point group end===========");
        log.debug("==========MigExecutor End===========");
    }
}
