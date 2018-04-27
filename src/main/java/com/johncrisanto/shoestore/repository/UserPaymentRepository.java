package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {

}
