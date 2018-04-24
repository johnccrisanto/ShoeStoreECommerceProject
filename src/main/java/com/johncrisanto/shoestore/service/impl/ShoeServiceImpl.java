package com.johncrisanto.shoestore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.repository.ShoeRepository;
import com.johncrisanto.shoestore.service.ShoeService;

@Service
public class ShoeServiceImpl implements ShoeService {
	
	@Autowired
	private ShoeRepository shoeRepository;

	@Override
	public List<Shoe> findAll() {
		return (List<Shoe>)shoeRepository.findAll();
	}

	@Override
	public Shoe findById(Long id) {
		
		return shoeRepository.findById(id).orElse(null);
	}
	
	
	
	
	
	

}
