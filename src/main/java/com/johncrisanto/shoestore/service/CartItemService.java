package com.johncrisanto.shoestore.service;

import java.util.List;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.Order;
import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.entity.ShoppingCart;
import com.johncrisanto.shoestore.entity.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	CartItem updateCartItem(CartItem cartItem);

	CartItem addShoeToCartItem(Shoe shoe, User user, int qty);

	CartItem findById(Long cartItemId);

	void removeCartItem(CartItem cartItem);

	CartItem save(CartItem cartItem);

	List<CartItem> findByOrder(Order order);
}
