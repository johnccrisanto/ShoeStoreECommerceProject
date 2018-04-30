package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.BillingAddress;
import com.johncrisanto.shoestore.entity.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
