package com.gc.bob.mig.point.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gc.bob.mig.point.entity.Point;

public interface PointRepository extends MongoRepository<Point, String> {

}
