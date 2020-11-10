package com.gc.bob.mig.point.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gc.bob.mig.point.entity.AdministrativeArea;

public interface AdministrativeAreaRepository extends MongoRepository<AdministrativeArea, String> {
    List<AdministrativeArea> findByType(String type);

    List<AdministrativeArea> findByLocationExists(Boolean exist);
}
