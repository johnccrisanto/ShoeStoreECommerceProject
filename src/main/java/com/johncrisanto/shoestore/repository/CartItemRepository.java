package com.johncrisanto.shoestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.Order;
import com.johncrisanto.shoestore.entity.ShoppingCart;

@Transactional 
public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	List<CartItem> findByOrder(Order order);
}
