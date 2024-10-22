package com.example.two_datasources.egain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
public class EGainUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String status = "EMPLOYEE";

}
