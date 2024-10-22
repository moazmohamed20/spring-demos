package com.example.user_roles_microservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_roles_microservice.entity.User;

public interface UsersRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}
