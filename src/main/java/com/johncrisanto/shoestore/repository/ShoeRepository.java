package com.johncrisanto.shoestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.Shoe;

public interface ShoeRepository extends CrudRepository<Shoe, Long> {

	List<Shoe> findByCategory(String category);

	List<Shoe> findByNewArrivalTrue();

	List<Shoe> findByNameContaining(String keyword);
	
}
