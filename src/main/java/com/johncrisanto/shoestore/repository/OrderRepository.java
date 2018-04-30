package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
