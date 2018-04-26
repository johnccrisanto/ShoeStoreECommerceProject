package com.johncrisanto.shoestore.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.UserBilling;
import com.johncrisanto.shoestore.entity.UserPayment;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;
import com.johncrisanto.shoestore.entity.security.UserRole;
import com.johncrisanto.shoestore.repository.PasswordResetTokenRepository;
import com.johncrisanto.shoestore.repository.RoleRepository;
import com.johncrisanto.shoestore.repository.UserRepository;
import com.johncrisanto.shoestore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
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

	@Override
	public User createUser(User user, Set<UserRole> userRoleSet) throws Exception {
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("User {} already exists.", user.getUsername());
		} else {
			for(UserRole userRole: userRoleSet) {
				roleRepository.save(userRole.getRole());
			}
			
			user.getUserRoleSet().addAll(userRoleSet);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		save(user);
		
	}
	
	

}
