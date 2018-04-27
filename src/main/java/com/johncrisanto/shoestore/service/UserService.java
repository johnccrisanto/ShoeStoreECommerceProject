package com.johncrisanto.shoestore.service;

import java.util.Set;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.UserBilling;
import com.johncrisanto.shoestore.entity.UserPayment;
import com.johncrisanto.shoestore.entity.security.UserRole;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createUser(User user, Set<UserRole> userRoleSet) throws Exception;
	
	User save(User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);
}
