package com.unext.capstone.promotionsandoffers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.unext.capstone.promotionsandoffers.model.Discount;

@Repository
public interface DiscountRepo extends MongoRepository<Discount, Integer> {
  //@Query(value="{}", fields = "{'discountId':1}")
  //public List<String> getProductByName();
}
