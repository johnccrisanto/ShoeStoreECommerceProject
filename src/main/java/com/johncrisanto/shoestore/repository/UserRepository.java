package com.johncrisanto.shoestore.repository;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.security.UserRole;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    
    User findByEmail(String email);
 
}
