package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.BillingAddress;
import com.johncrisanto.shoestore.entity.Order;
import com.johncrisanto.shoestore.entity.Payment;
import com.johncrisanto.shoestore.entity.ShippingAddress;
import com.johncrisanto.shoestore.entity.ShoppingCart;
import com.johncrisanto.shoestore.entity.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user);
	
	Order findById(Long id);
}
