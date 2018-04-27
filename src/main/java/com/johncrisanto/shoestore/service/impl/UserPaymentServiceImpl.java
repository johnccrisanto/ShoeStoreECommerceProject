package com.johncrisanto.shoestore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.UserPayment;
import com.johncrisanto.shoestore.repository.UserPaymentRepository;
import com.johncrisanto.shoestore.service.UserPaymentService;

@Service 
public class UserPaymentServiceImpl implements UserPaymentService {

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Override
	public UserPayment findById(Long creditCardId) {
		return userPaymentRepository.findById(creditCardId).orElse(null);
	}
	
	

}
