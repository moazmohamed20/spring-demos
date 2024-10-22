package com.example.two_datasources.egain.repository;

import com.example.two_datasources.egain.entity.EGainUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EGainUserRepository extends JpaRepository<EGainUser, Integer> {

    EGainUser findByStatus(String status);
    
}
