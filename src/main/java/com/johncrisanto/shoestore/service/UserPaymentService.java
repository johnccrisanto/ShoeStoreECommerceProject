package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.UserPayment;

public interface UserPaymentService {

	 UserPayment findById(Long creditCardId);

	void removeById(Long creditCardId);
	
}
