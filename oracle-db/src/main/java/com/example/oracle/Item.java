package com.example.oracle;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private int quantity;
  private double price;

  @CreationTimestamp
  private Instant createdAt;

  @Lob
  private String content;

  @Enumerated(EnumType.STRING)
  private Status status;

  public static enum Status {
    ACTIVE, INACTIVE
  }

}