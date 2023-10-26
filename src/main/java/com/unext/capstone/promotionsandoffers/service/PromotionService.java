package com.unext.capstone.promotionsandoffers.service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unext.capstone.promotionsandoffers.exception.DiscountNotExistsException;
import com.unext.capstone.promotionsandoffers.exception.PromotionAlreadyExistsException;
import com.unext.capstone.promotionsandoffers.exception.PromotionInvalidException;
import com.unext.capstone.promotionsandoffers.exception.PromotionNotAvailableException;
import com.unext.capstone.promotionsandoffers.exception.PromotionNotExistsException;
import com.unext.capstone.promotionsandoffers.exception.UsageInvalidException;
import com.unext.capstone.promotionsandoffers.model.Discount;
import com.unext.capstone.promotionsandoffers.model.Promotion;
import com.unext.capstone.promotionsandoffers.model.PromotionByUser;
import com.unext.capstone.promotionsandoffers.model.PromotionByUserId;
import com.unext.capstone.promotionsandoffers.repository.PromotionByUserRepo;
import com.unext.capstone.promotionsandoffers.repository.PromotionRepo;

@Service
public class PromotionService {
	@Autowired
	PromotionRepo repo;
	@Autowired
	PromotionByUserRepo userRepo;
	public boolean flag=false;
	
	public Promotion insertPromotionDetails(Promotion promo) throws PromotionAlreadyExistsException, PromotionInvalidException ,Exception
	{
		//Check if the Promotion already exists in the database and throw exception
		Optional<Promotion> opt=this.repo.findById(promo.getPromoCode());
		if(opt.isPresent()) {
			throw new PromotionAlreadyExistsException();
		}
		// Throw exception when percentage or number of promo's are invalid
		if(promo.getPromoPercentage()<0 || promo.getNbrOfPromos()<0 ||
				promo.getPromoPercentage()>99 || promo.getNbrOfPromos()>999 )
			throw new PromotionInvalidException();
		//Save and return promotion collection record
		repo.save(promo);
		return promo;
	}
	
	public Promotion updatePromotionDetails(Promotion promo) throws PromotionInvalidException , Exception
	{	
		// Throw exception when percentage or number of promo's are invalid
		if(promo.getPromoPercentage()<0 || promo.getNbrOfPromos()<0 ||
			promo.getPromoPercentage()>99 || promo.getNbrOfPromos()>999 )
			throw new PromotionInvalidException();
		
		Optional<Promotion> opt=this.repo.findById(promo.getPromoCode());
		//whenever no values for the fields are passed in the pay-load, data will be fetched from Promotion collection 
		if(opt.isPresent()) {
			if(promo.getStartDate()==null)
				promo.setStartDate(opt.get().getStartDate());
			if(promo.getEndDate()==null)
				promo.setEndDate(opt.get().getEndDate());
			if(promo.getCreatedDate()==null)
				promo.setCreatedDate(opt.get().getCreatedDate());
			if(promo.getPromoPercentage()==0)
				promo.setPromoPercentage(opt.get().getPromoPercentage());
			if(promo.getNbrOfPromos()==0)
				promo.setNbrOfPromos(opt.get().getNbrOfPromos());
		}
		//Save and return promotion collection record
		repo.save(promo);
		return promo;
	}
	
	public Promotion deletePromotionDetails(String promoCode) throws PromotionNotExistsException ,Exception
	{
		//Check if the promotion exists in the database and throw exception
		Optional<Promotion> opt=this.repo.findById(promoCode);
		if(!opt.isPresent()) {
			throw new PromotionNotExistsException();
		}
		
		//delete and return Promotion collection record
		repo.delete(opt.get());
		return opt.get();
	}
	
	public List<Promotion> getPromotionDetails(String userId) throws PromotionNotAvailableException, Exception
	{
		
		Optional<List<Promotion>> opt = Optional.of(this.repo.findAll());
		List<Promotion> availablepromos =opt.get();
		
		System.out.println(availablepromos);
		if(!opt.isPresent())
		{
			throw new PromotionNotAvailableException();
		}
		else
		{
			Iterator <Promotion> iter = availablepromos.iterator();
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy/MM/dd");
			Date cDate = new Date();
			String stringDate =sdf.format(cDate);
			Date currentDate =sdf.parse(stringDate);
			System.out.println(currentDate);
			while(iter.hasNext())
			{
				Promotion promo =iter.next();
				PromotionByUserId puid = new PromotionByUserId(userId,promo.getPromoCode());
				//System.out.println(puid.toString());
				Optional<PromotionByUser> userPromoDetails = (this.userRepo.findById(puid));
				//System.out.println(userPromoDetails.toString());
				//System.out.println(promo.getPromoCode()+currentDate.before(promo.getEndDate()));
				//System.out.println(promo.getPromoCode()+currentDate.after(promo.getStartDate()));
				if(currentDate.before(promo.getStartDate()))
				{
					//System.out.println("condition satisfying");
					iter.remove();
				}
				else if(currentDate.after(promo.getEndDate()))
				{
					//System.out.println("condition satisfying");
					iter.remove();
				}
				else if(!userPromoDetails.isPresent())
				{
		             continue;
				}
				else if(userPromoDetails.get().getNbrOfPromos()>=promo.getNbrOfPromos())
				{
					iter.remove();
				}
				
				
				
			}
			iter.forEachRemaining(availablepromos::add);
		}
		return availablepromos;
		
	}

	public PromotionByUser insertPromotionByUser(PromotionByUser userPromo) throws UsageInvalidException, Exception
	{
		// Throw exception number of promo's are invalid
		if(userPromo.getNbrOfPromos()<=0 )
			throw new UsageInvalidException();
		userRepo.save(userPromo);
		return userPromo;

	}
	public PromotionByUser updatePromotionByUser(PromotionByUser userPromo) throws UsageInvalidException, Exception
	{
		// Throw exception when percentage or number of promo's are invalid
		if(userPromo.getNbrOfPromos()<=0 )
			throw new UsageInvalidException();
		
		Optional<PromotionByUser> existingDetails = this.userRepo.findById(userPromo.getId());
		int oldUsage=existingDetails.get().getNbrOfPromos();
		oldUsage= oldUsage+userPromo.getNbrOfPromos();
		existingDetails.get().setNbrOfPromos(oldUsage);
		existingDetails.get().setlastUsedOn(userPromo.getlastUsedOn());
		existingDetails.get().setId(userPromo.getId());
		userRepo.save(existingDetails.get());
		return existingDetails.get();

	}
	public boolean checkPromotionsByUser(PromotionByUser userPromo) {
		
		PromotionByUserId puid = userPromo.getId();
		
		Optional<PromotionByUser> opt = this.userRepo.findById(puid);
		if(!opt.isPresent())
		{
			this.flag = true;	
		}

		return flag;
	}
}

