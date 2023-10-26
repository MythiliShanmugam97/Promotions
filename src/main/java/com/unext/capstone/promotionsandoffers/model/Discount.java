package com.unext.capstone.promotionsandoffers.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection="Discount")
public class Discount {
		@Id
		int productId;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date createdDate;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date startDate;
		@JsonFormat(pattern="yyyy/MM/dd")
		Date endDate;
		double discountPercentage;	
		
		public Discount() {}
		
		public Discount(int productId, Date createdDate, Date startDate, Date endDate, double discountPercentage) {
			super();
			this.productId = productId;
			this.createdDate = createdDate;
			this.startDate = startDate;
			this.endDate = endDate;
			this.discountPercentage = discountPercentage;
		}

		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
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
		public double getDiscountPercentage() {
			return discountPercentage;
		}
		public void setDiscountPercentage(double discountPercentage) {
			this.discountPercentage = discountPercentage;
		}

		@Override
		public String toString() {
			return "Discount [productId=" + productId + ", createdDate=" + createdDate
					+ ", startDate=" + startDate + ", endDate=" + endDate + ", discountPercentage=" + discountPercentage
					+ "]";
		}
		
		
}
