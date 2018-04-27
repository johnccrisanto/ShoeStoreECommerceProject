package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.ShoeToCartItem;

public interface ShoeToCartItemRepository extends CrudRepository<ShoeToCartItem, Long> {

}
