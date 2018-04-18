package com.johncrisanto.shoestore.repository;
import com.johncrisanto.shoestore.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
