package com.example.two_datasources.local.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

}
