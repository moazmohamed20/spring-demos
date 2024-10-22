package com.example.openfeign.model;

import java.util.List;

import lombok.Data;

@Data
public class Page {
  private List<Product> products;
  private int total;
  private int skip;
  private int limit;
}