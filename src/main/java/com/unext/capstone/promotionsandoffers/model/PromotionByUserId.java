package com.unext.capstone.promotionsandoffers.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PromotionByUserId implements Serializable{
	
	public PromotionByUserId() {
		super();
	}

	public PromotionByUserId(String usedBy, String promoCode) {
		this.usedBy=usedBy;
		this.promotionCode=promoCode;
	}
	private String promotionCode;
	private String usedBy;
	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

	@Override
	public String toString() {
		return "PromotionByUserId [promotionCode=" + promotionCode + ", usedBy=" + usedBy + "]";
	}
	
	


	
	

}
