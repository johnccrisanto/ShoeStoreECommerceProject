package com.johncrisanto.shoestore.repository;
import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    
    User findByEmail(String email);
 
}
