package com.johncrisanto.shoestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.johncrisanto.shoestore.entity.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
//	Role findByName(String name);
}
