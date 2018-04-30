package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.Payment;
import com.johncrisanto.shoestore.entity.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}


