package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.ShippingAddress;
import com.johncrisanto.shoestore.entity.UserShipping;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
