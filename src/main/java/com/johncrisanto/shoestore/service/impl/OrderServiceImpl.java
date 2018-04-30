package com.johncrisanto.shoestore.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.BillingAddress;
import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.Order;
import com.johncrisanto.shoestore.entity.Payment;
import com.johncrisanto.shoestore.entity.ShippingAddress;
import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.entity.ShoppingCart;
import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.repository.OrderRepository;
import com.johncrisanto.shoestore.service.CartItemService;
import com.johncrisanto.shoestore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Shoe shoe = cartItem.getShoe();
			cartItem.setOrder(order);
			shoe.setInStockNumber(shoe.getInStockNumber() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

}
