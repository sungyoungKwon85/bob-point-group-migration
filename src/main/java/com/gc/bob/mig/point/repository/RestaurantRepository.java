package com.gc.bob.mig.point.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gc.bob.mig.point.entity.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

}