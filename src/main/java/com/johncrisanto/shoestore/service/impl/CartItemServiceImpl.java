package com.johncrisanto.shoestore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.entity.ShoeToCartItem;
import com.johncrisanto.shoestore.entity.ShoppingCart;
import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.repository.CartItemRepository;
import com.johncrisanto.shoestore.repository.ShoeToCartItemRepository;
import com.johncrisanto.shoestore.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ShoeToCartItemRepository shoeToCartItemRepository;
	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		
		BigDecimal bigDecimal = new BigDecimal(cartItem.getShoe().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		return cartItem;
		
	}

	@Override
	public CartItem addShoeToCartItem(Shoe shoe, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for(CartItem cartItem: cartItemList) {
			if(shoe.getId() == cartItem.getShoe().getId()) {
				cartItem.setQty(cartItem.getQty());
				cartItem.setSubtotal(new BigDecimal(shoe.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setShoe(shoe);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(shoe.getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		
		ShoeToCartItem shoeToCartItem = new ShoeToCartItem();
		shoeToCartItem.setShoe(shoe);
		shoeToCartItem.setCartItem(cartItem);
		shoeToCartItemRepository.save(shoeToCartItem);
		
		return cartItem;
		
	}
	
	
	

}
