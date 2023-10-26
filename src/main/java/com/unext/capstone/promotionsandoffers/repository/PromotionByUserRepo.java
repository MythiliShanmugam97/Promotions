package com.unext.capstone.promotionsandoffers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unext.capstone.promotionsandoffers.model.Promotion;
import com.unext.capstone.promotionsandoffers.model.PromotionByUser;
import com.unext.capstone.promotionsandoffers.model.PromotionByUserId;

public interface PromotionByUserRepo extends MongoRepository<PromotionByUser,PromotionByUserId> { }
