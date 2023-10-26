package com.unext.capstone.promotionsandoffers.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unext.capstone.promotionsandoffers.exception.PromotionNotExistsException;
import com.unext.capstone.promotionsandoffers.exception.UsageInvalidException;
import com.unext.capstone.promotionsandoffers.exception.PromotionAlreadyExistsException;
import com.unext.capstone.promotionsandoffers.exception.PromotionInvalidException;
import com.unext.capstone.promotionsandoffers.exception.PromotionNotAvailableException;
import com.unext.capstone.promotionsandoffers.model.Promotion;
import com.unext.capstone.promotionsandoffers.model.PromotionByUser;
import com.unext.capstone.promotionsandoffers.service.PromotionService;

@RestController
@RequestMapping("/promotion") 
public class PromotionController {

	@Autowired
	PromotionService pserv;
	ResponseEntity<?> resentity;
		
	@PostMapping("/createPromotion")
	public ResponseEntity<?> createPromotion(@RequestBody Promotion promo)
	{
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try { 
			resInfo.put("status",1);
			resInfo.put("message","Promotion created successfully");
			resInfo.put("data",pserv.insertPromotionDetails(promo));
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.CREATED);
		}
		catch(PromotionInvalidException e) {
			resInfo.put("status",0);
			resInfo.put("message","Promotion % (or) number of promo's are not valid");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(PromotionAlreadyExistsException e) {
			resInfo.put("status",0);
			resInfo.put("message","Promo-Code already exists in database");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Promotion creation Failed,pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@PutMapping("/updatePromotion")
	public ResponseEntity<?> updatePromotion(@RequestBody Promotion promo) 
	{
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try {
			resInfo.put("status",1);
			resInfo.put("message","Promotion updated successfully");
			resInfo.put("data",pserv.updatePromotionDetails(promo));
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		catch(PromotionInvalidException e) {
			resInfo.put("status",0);
			resInfo.put("message","Promotion % (or) number of promo's are not valid");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Promotion updation Failed, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@PutMapping("/updatePromotions")
	public ResponseEntity<?> updatePromotions(@RequestBody List<Promotion> promos) 
	{
		List<Promotion> savedPromos = new ArrayList<Promotion>();
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		boolean success=true;
		
		//process bulk volume of promos from input
		for (Promotion promo:promos) {
			try {
				savedPromos.add(pserv.updatePromotionDetails(promo));	
			}
			catch(PromotionInvalidException e) {
				success=false;
				resInfo.put("status",0);
				resInfo.put("message","Promotion % (or) number of promo's are not valid, Only few Promotions might have been updated");
			}
			catch(Exception e){
				success=false;
				System.out.println(e);
				resInfo.put("status",0);
				resInfo.put("message","Only few Promotions might have been updated, pls try later");
			}
		}
		if (success) {
			//when update succeeded, send all the promos
			resInfo.put("status",1);
			resInfo.put("message","Promotions updated successfully");
			resInfo.put("data",savedPromos);
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		else {
			//when update failed, send only processed promos
			resInfo.put("data",savedPromos);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@DeleteMapping("/deletePromotion/{pc}")
	public ResponseEntity<?> deleteDiscount(@PathVariable("pc") String promoCode)
	{	
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try { 
			resInfo.put("data",pserv.deletePromotionDetails(promoCode));
			resInfo.put("status",1);
			resInfo.put("message","Promotion deleted successfully");
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		catch(PromotionNotExistsException e) {
			resInfo.put("status",0);
			resInfo.put("message","Promotion not exists in database");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Promotion deletion Failed, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	@GetMapping("/getActivePromotions/{userId}")
	public ResponseEntity<?> getActivePromotion (@PathVariable("userId")String userId)
	{
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try { 
			resInfo.put("data",pserv.getPromotionDetails(userId));
			resInfo.put("status",1);
			resInfo.put("message","Retrieved all Promotion details successfully");
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		catch(PromotionNotAvailableException e)
		{
			resInfo.put("status",0);
			resInfo.put("message","Promotion details are not yet available in database");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			resInfo.put("status",0);
			resInfo.put("message","Promotion Retrival failure, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resentity;
	}
	
	@PostMapping("/updatePromotionsByUser")
	public ResponseEntity<?> updatePromotionsByUser (@RequestBody PromotionByUser userPromo) throws UsageInvalidException, Exception
	{
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
			boolean flag= pserv.checkPromotionsByUser(userPromo);
			System.out.println("userPromo-2");
			if(flag)
			{
				try
				{
				resInfo.put("data",pserv.insertPromotionByUser(userPromo));;
				resInfo.put("status",1);
				resInfo.put("message","Inserted PromoCode Usage of User successfully");
				resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
			    }
				catch(UsageInvalidException e)
				{
					resInfo.put("status",0);
					resInfo.put("message","Usage Details provided is Incorrect ");
					resInfo.put("data",null);
					resentity=new ResponseEntity<>(resInfo,HttpStatus.BAD_REQUEST);
				
			    }
				catch (Exception e)
				{
					resInfo.put("status",0);
					resInfo.put("message","Usage Details Insert Failure, pls try later");
					resInfo.put("data",null);
					resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
			
		     }
			else
			{
				try
				{
				resInfo.put("data",pserv.updatePromotionByUser(userPromo));
				resInfo.put("status",1);
				resInfo.put("message","Updated PromoCode Usage of User successfully");
				resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
			    }
				catch(UsageInvalidException e)
				{
					resInfo.put("status",0);
					resInfo.put("message","Usage Details provided is Incorrect ");
					resInfo.put("data",null);
					resentity=new ResponseEntity<>(resInfo,HttpStatus.BAD_REQUEST);
				
			    }
				catch (Exception e)
				{
					resInfo.put("status",0);
					resInfo.put("message","Usage Details Insert Failure, pls try later");
					resInfo.put("data",null);
					resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
				
			}
		
		return resentity;
	}
	
}
