package com.istnetworks.soap_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.istnetworks.soap_demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
