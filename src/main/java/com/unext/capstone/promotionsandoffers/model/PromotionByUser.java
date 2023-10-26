package com.unext.capstone.promotionsandoffers.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="PromotionByUser")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PromotionByUser {
	
    @Id
    private PromotionByUserId id;
	int nbrOfPromos;
	@JsonFormat(pattern="yyyy/MM/dd")
	Date lastUsedOn;
	public PromotionByUserId getId() {
		return id;
	}
	public void setId(PromotionByUserId id) {
		this.id = id;
	}
	public int getNbrOfPromos() {
		return nbrOfPromos;
	}
	public void setNbrOfPromos(int nbrOfPromos) {
		this.nbrOfPromos = nbrOfPromos;
	}
	public Date getlastUsedOn() {
		return lastUsedOn;
	}
	public void setlastUsedOn(Date lastUsedOn) {
		this.lastUsedOn = lastUsedOn;
	}
	public PromotionByUser(PromotionByUserId id, int nbrOfPromos, Date lastUsedOn) {
		super();
		this.id = id;
		this.nbrOfPromos = nbrOfPromos;
		this.lastUsedOn = lastUsedOn;
	}
	public PromotionByUser() {
		super();
	}
	@Override
	public String toString() {
		return "PromotionByUser [id=" + id + ", nbrOfPromos=" + nbrOfPromos + ", lastUsedOn=" + lastUsedOn + "]";
	}
	
	
	

}
