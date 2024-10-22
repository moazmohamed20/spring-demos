package com.example.user_roles_microservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_roles_microservice.entity.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);

}
