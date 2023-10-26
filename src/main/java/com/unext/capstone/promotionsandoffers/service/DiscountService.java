package com.unext.capstone.promotionsandoffers.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unext.capstone.promotionsandoffers.exception.DiscountAlreadyExistsException;
import com.unext.capstone.promotionsandoffers.exception.DiscountInvalidException;
import com.unext.capstone.promotionsandoffers.exception.DiscountNotExistsException;
import com.unext.capstone.promotionsandoffers.model.Discount;
import com.unext.capstone.promotionsandoffers.repository.DiscountRepo;

@Service
public class DiscountService {
	@Autowired
	DiscountRepo repo;
	
	public Discount insertDiscountDetails(Discount discount) throws DiscountAlreadyExistsException,DiscountInvalidException ,Exception
	{
		// Throw exception when discount percentage is invalid
		if(discount.getDiscountPercentage()<0 || discount.getDiscountPercentage()>99 )
			throw new DiscountInvalidException();
				
		//Check if the discount already exists in the database and throw exception
		Optional<Discount> opt=this.repo.findById((int) discount.getProductId());
		if(opt.isPresent()) {
			throw new DiscountAlreadyExistsException();
		}
		
		//Save and return Discount collection record
		repo.save(discount);
		return discount;
	}
	
	public Discount updateDiscountDetails(Discount discount) throws DiscountInvalidException , Exception
	{	
		// Throw exception when discount percentage is invalid
		if(discount.getDiscountPercentage()<0 ||discount.getDiscountPercentage()>99 )
			throw new DiscountInvalidException();
					
		Optional<Discount> opt=this.repo.findById((int) discount.getProductId());
		//whenever no values for the fields are passed in the pay-load, data will be fetched from Discount collection 
		if(opt.isPresent()) {
			if(discount.getStartDate()==null)
				discount.setStartDate(opt.get().getStartDate());
			if(discount.getEndDate()==null)
				discount.setEndDate(opt.get().getEndDate());
			if(discount.getCreatedDate()==null)
				discount.setCreatedDate(opt.get().getCreatedDate());
			if(discount.getDiscountPercentage()==0.0)
				discount.setDiscountPercentage(opt.get().getDiscountPercentage());
		}
	
		//Save and return Discount collection record
		repo.save(discount);
		return discount;
	}
	
	public Discount deleteDiscountDetails(int productId) throws DiscountNotExistsException ,Exception
	{
		//Check if the discount exists in the database and throw exception
		Optional<Discount> opt=this.repo.findById(productId);
		if(!opt.isPresent()) {
			throw new DiscountNotExistsException();
		}
		
		//delete and return Discount collection record
		repo.delete(opt.get());
		return opt.get();
	}
	
}