package com.johncrisanto.shoestore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity 
public class UserBilling {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userBillingName;
	private String userBillingStreet;
	private String userBillingStreet2;
	private String userBillingCity;
	private String userBillingState;
	private String userBillingZipCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserPayment userPayment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserBillingName() {
		return userBillingName;
	}

	public void setUserBillingName(String userBillingName) {
		this.userBillingName = userBillingName;
	}

	public String getUserBillingStreet() {
		return userBillingStreet;
	}

	public void setUserBillingStreet(String userBillingStreet) {
		this.userBillingStreet = userBillingStreet;
	}

	public String getUserBillingStreet2() {
		return userBillingStreet2;
	}

	public void setUserBillingStreet2(String userBillingStreet2) {
		this.userBillingStreet2 = userBillingStreet2;
	}

	public String getUserBillingCity() {
		return userBillingCity;
	}

	public void setUserBillingCity(String userBillingCity) {
		this.userBillingCity = userBillingCity;
	}

	public String getUserBillingState() {
		return userBillingState;
	}

	public void setUserBillingState(String userBillingState) {
		this.userBillingState = userBillingState;
	}

	public String getUserBillingZipCode() {
		return userBillingZipCode;
	}

	public void setUserBillingZipCode(String userBillingZipCode) {
		this.userBillingZipCode = userBillingZipCode;
	}

	public UserPayment getUserPayment() {
		return userPayment;
	}

	public void setUserPayment(UserPayment userPayment) {
		this.userPayment = userPayment;
	}

	
	
	

}
