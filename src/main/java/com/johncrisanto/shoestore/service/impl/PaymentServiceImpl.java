package com.johncrisanto.shoestore.service.impl;

import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.Payment;
import com.johncrisanto.shoestore.entity.UserPayment;
import com.johncrisanto.shoestore.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setCardType(userPayment.getCardType());
		payment.setCardHolderName(userPayment.getCardHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());
		
		return payment;
	}

}
