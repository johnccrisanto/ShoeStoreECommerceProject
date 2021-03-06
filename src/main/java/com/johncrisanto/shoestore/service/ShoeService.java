package com.johncrisanto.shoestore.service;

import java.util.List;

import com.johncrisanto.shoestore.entity.Shoe;

public interface ShoeService {
	List<Shoe> findAll();

	Shoe findById(Long id);

	List<Shoe> findByCategory(String category);

	List<Shoe> findByNewArrivalTrue();

	List<Shoe> blurrySearch(String keyword);
}
