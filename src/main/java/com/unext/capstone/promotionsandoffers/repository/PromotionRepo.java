package com.unext.capstone.promotionsandoffers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unext.capstone.promotionsandoffers.model.Promotion;

@Repository
public interface PromotionRepo extends MongoRepository<Promotion, String> { }
