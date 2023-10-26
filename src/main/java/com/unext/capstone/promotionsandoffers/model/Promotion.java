package com.unext.capstone.promotionsandoffers.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection="Promotion")
public class Promotion {
		@Id
		String promoCode;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date createdDate;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date startDate;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date endDate;
		double promoPercentage;	
		int nbrOfPromos;
		
		public Promotion() {}
		
		public Promotion(String promoCode, Date createdDate, Date startDate, Date endDate,
				double promoPercentage, int nbrOfPromos) {
			super();
			this.promoCode = promoCode;
			this.createdDate = createdDate;
			this.startDate = startDate;
			this.endDate = endDate;
			this.promoPercentage = promoPercentage;
			this.nbrOfPromos = nbrOfPromos;
		}
		public String getPromoCode() {
			return promoCode;
		}
		public void setPromoCode(String promoCode) {
			this.promoCode = promoCode;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public double getPromoPercentage() {
			return promoPercentage;
		}
		public void setPromoPercentage(double promoPercentage) {
			this.promoPercentage = promoPercentage;
		}
		public int getNbrOfPromos() {
			return nbrOfPromos;
		}
		public void setNbrOfPromos(int nbrOfPromos) {
			this.nbrOfPromos = nbrOfPromos;
		}
		@Override
		public String toString() {
			return "Promotion [promoCode=" + promoCode + ", createdDate=" + createdDate
					+ ", startDate=" + startDate + ", endDate=" + endDate + ", promoPercentage=" + promoPercentage
					+ ", nbrOfPromos=" + nbrOfPromos + "]";
		}
				
}
