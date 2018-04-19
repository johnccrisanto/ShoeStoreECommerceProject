package com.johncrisanto.shoestore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.security.Role;
import com.johncrisanto.shoestore.entity.security.UserRole;
import com.johncrisanto.shoestore.service.UserService;
import com.johncrisanto.shoestore.utility.SecurityUtility;

@SpringBootApplication
public class ShoestoreApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		
		SpringApplication.run(ShoestoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Crisanto");
		user1.setUsername("jcrisanto");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("password"));
		user1.setEmail("jcrisanto@gmail.com");
		Set<UserRole> userRoleSet = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setRoleName("ROLE_USER");
		userRoleSet.add(new UserRole(user1, role1));
		userService.createUser(user1, userRoleSet);
	}
	
	
}
