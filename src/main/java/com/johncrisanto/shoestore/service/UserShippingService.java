package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.UserShipping;

public interface UserShippingService {

	UserShipping findById(Long shippingAddressId);

	void removeById(Long userShippingId);

}
