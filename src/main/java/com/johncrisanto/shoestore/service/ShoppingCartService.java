package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.ShoppingCart;

public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);
}
