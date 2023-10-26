package com.unext.capstone.promotionsandoffers.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unext.capstone.promotionsandoffers.exception.DiscountInvalidException;
import com.unext.capstone.promotionsandoffers.exception.DiscountNotExistsException;
import com.unext.capstone.promotionsandoffers.exception.DiscountAlreadyExistsException;
import com.unext.capstone.promotionsandoffers.model.Discount;
import com.unext.capstone.promotionsandoffers.service.DiscountService;

@RestController
@RequestMapping("/discount") 
public class DiscountController {

	@Autowired
	DiscountService dserv;
	ResponseEntity<?> resentity;
	
	@PostMapping("/createDiscount")
	public ResponseEntity<?> createDiscount(@RequestBody Discount discount)
	{	
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try { 
			resInfo.put("status",1);
			resInfo.put("message","Discount created successfully");
			resInfo.put("data",dserv.insertDiscountDetails(discount));
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.CREATED);
		}
		catch(DiscountInvalidException e) {
			resInfo.put("status",0);
			resInfo.put("message","Discount % is not valid");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(DiscountAlreadyExistsException e) {
			resInfo.put("status",0);
			resInfo.put("message","Discount already exists for the product in database");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Discount creation Failed, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@PutMapping("/updateDiscount")
	public ResponseEntity<?> updateDiscount(@RequestBody Discount discount) 
	{
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try {
			resInfo.put("status",1);
			resInfo.put("message","Discount updated successfully");
			resInfo.put("data",dserv.updateDiscountDetails(discount));
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		catch(DiscountInvalidException e) {
			resInfo.put("status",0);
			resInfo.put("message","Discount % is not valid");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Discount updation Failed, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@PutMapping("/updateDiscounts")
	public ResponseEntity<?> updateDiscounts(@RequestBody List<Discount> discounts) 
	{
		List<Discount> savedDiscounts = new ArrayList<Discount>();
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		boolean success=true;
		
		//process bulk volume of discounts from input
		for (Discount discount:discounts) {
			try {	
				savedDiscounts.add(dserv.updateDiscountDetails(discount));	
			}
			catch(DiscountInvalidException e) {
				success=false;
				resInfo.put("status",0);			
				resInfo.put("message","Discount % is not valid, Only few discounts might have been updated");
			}
			catch(Exception e){
				success=false;
				System.out.println(e);
				resInfo.put("status",0);
				resInfo.put("message","Only few discounts might have been updated, pls try later");
			}
		}
		if (success) {
			//when update succeeded, send all the discounts
			resInfo.put("status",1);
			resInfo.put("message","Discounts updated successfully");
			resInfo.put("data",savedDiscounts);
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		else {
			//when update failed, send only processed discounts
			resInfo.put("data",savedDiscounts);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
	@DeleteMapping("/deleteDiscount/{pid}")
	public ResponseEntity<?> deleteDiscount(@PathVariable("pid") int productId)
	{	
		Map<String, Object> resInfo = new LinkedHashMap<String, Object>();
		try { 
			resInfo.put("status",1);
			resInfo.put("message","Discount deleted successfully");
			resInfo.put("data",dserv.deleteDiscountDetails(productId));
			resentity=new ResponseEntity<>(resInfo ,HttpStatus.OK);
		}
		catch(DiscountNotExistsException e) {
			resInfo.put("status",0);
			resInfo.put("message","Discount not exists for the product in database");
			resInfo.put("data",null);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			resInfo.put("status",0);
			resInfo.put("message","Discount deletion Failed, pls try later");
			resInfo.put("data",null);
			System.out.println(e);
			resentity=new ResponseEntity<>(resInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resentity;
	}
	
}
