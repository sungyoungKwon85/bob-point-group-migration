package com.gc.bob.mig.point.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gc.bob.mig.point.entity.AdministrativeArea;
import com.gc.bob.mig.point.repository.AdministrativeAreaRepository;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrativeCenterMigrationServiceTest {

    @Autowired
    private AdministrativeCenterMigrationService migrationService;

//    @Test
//    void migrateCenterOfAdministrativeArea_testOne() {
//        migrationService.migrateCenterOfAdministrativeArea_missing();
//    }
}