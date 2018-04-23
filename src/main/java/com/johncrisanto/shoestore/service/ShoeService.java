package com.johncrisanto.shoestore.service;

import java.util.List;

import com.johncrisanto.shoestore.entity.Shoe;

public interface ShoeService {
	List<Shoe> findAll();
}
