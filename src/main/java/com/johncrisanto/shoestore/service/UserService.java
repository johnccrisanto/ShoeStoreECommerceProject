package com.johncrisanto.shoestore.service;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
}
