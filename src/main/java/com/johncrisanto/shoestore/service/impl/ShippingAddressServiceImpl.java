package com.johncrisanto.shoestore.service.impl;

import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.ShippingAddress;
import com.johncrisanto.shoestore.entity.UserShipping;
import com.johncrisanto.shoestore.service.ShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
	public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
		shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
		shippingAddress.setShippingAddressStreet(userShipping.getUserShippingStreet());
		shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
		shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
		shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
		shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
		shippingAddress.setShippingAddressZipCode(userShipping.getUserShippingZipCode());
		
		return shippingAddress;
	}
}
