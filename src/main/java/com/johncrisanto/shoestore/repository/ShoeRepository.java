package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.Shoe;

public interface ShoeRepository extends CrudRepository<Shoe, Long> {
	
}
