package com.johncrisanto.shoestore.service.impl;

import java.util.ArrayList;
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
		
		List<Shoe> shoeList = (List<Shoe>)shoeRepository.findAll();
		List<Shoe> activeShoeList = new ArrayList<>();
		
		for(Shoe shoe: shoeList) {
			if(shoe.isActive()) {
				activeShoeList.add(shoe);
			}
		}
		
		return activeShoeList;
		
	}

	@Override
	public Shoe findById(Long id) {
		
		return shoeRepository.findById(id).orElse(null);
	}

	@Override
	public List<Shoe> findByCategory(String category) {


		List<Shoe> shoeList = shoeRepository.findByCategory(category);
		
		List<Shoe> activeShoeList = new ArrayList<>();
		
		for(Shoe shoe: shoeList) {
			if(shoe.isActive()) {
				activeShoeList.add(shoe);
			}
		}
		
		return activeShoeList;
	}

	@Override
	public List<Shoe> findByNewArrivalTrue() {
		
		
		List<Shoe> shoeList = shoeRepository.findByNewArrivalTrue();
		
		List<Shoe> newArrivalList = new ArrayList<>();
		
		for(Shoe shoe: shoeList) {
			if(shoe.isNewArrival()) {
				newArrivalList.add(shoe);
			}
		}
		
		return newArrivalList;
	}

	@Override
	public List<Shoe> blurrySearch(String keyword) {
		
		List<Shoe> shoeList = shoeRepository.findByNameContaining(keyword);
		
		List<Shoe> activeShoeList = new ArrayList<>();
		
		for(Shoe shoe: shoeList) {
			if(shoe.isActive()) {
				activeShoeList.add(shoe);
			}
		}
		
		return activeShoeList;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
