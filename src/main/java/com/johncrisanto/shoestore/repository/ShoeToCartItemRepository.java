package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.ShoeToCartItem;

@Transactional
public interface ShoeToCartItemRepository extends CrudRepository<ShoeToCartItem, Long> {

	void deleteByCartItem(CartItem cartItem);

}
