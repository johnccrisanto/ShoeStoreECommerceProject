package com.johncrisanto.shoestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
