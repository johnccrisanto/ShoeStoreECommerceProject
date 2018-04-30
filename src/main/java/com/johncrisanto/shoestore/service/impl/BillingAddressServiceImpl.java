package com.johncrisanto.shoestore.service.impl;

import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.BillingAddress;
import com.johncrisanto.shoestore.entity.UserBilling;
import com.johncrisanto.shoestore.service.BillingAddressService;

@Service
public class BillingAddressServiceImpl implements BillingAddressService{
	
	public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet(userBilling.getUserBillingStreet());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressState(userBilling.getUserBillingState());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		billingAddress.setBillingAddressZipCode(userBilling.getUserBillingZipCode());
		
		return billingAddress;
	}

}
