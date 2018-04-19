package com.johncrisanto.shoestore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;
import com.johncrisanto.shoestore.repository.PasswordResetTokenRepository;
import com.johncrisanto.shoestore.repository.UserRepository;
import com.johncrisanto.shoestore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
	
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	
	
}
