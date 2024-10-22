package com.example.two_datasources.local.repository;

import com.example.two_datasources.local.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByName(String username);

}
