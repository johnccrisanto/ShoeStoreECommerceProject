package com.johncrisanto.shoestore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.UserShipping;
import com.johncrisanto.shoestore.repository.UserShippingRepository;
import com.johncrisanto.shoestore.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {
	
	@Autowired
	UserShippingRepository userShippingRepository;

	@Override
	public UserShipping findById(Long shippingAddressId) {
		return userShippingRepository.findById(shippingAddressId).orElse(null);
	}

	@Override
	public void removeById(Long userShippingId) {
		userShippingRepository.deleteById(userShippingId);
		
	}
	
	

}
