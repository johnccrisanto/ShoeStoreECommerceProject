package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
