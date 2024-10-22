package com.example.openfeign.model;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class Product {
  private int id;
  private String title;
  private String description;
  private String category;
  private double price;
  private double discountPercentage;
  private double rating;
  private int stock;
  private List<String> tags;
  private String brand;
  private String sku;
  private int weight;
  private Dimensions dimensions;
  private String warrantyInformation;
  private String shippingInformation;
  private String availabilityStatus;
  private List<Review> reviews;
  private String returnPolicy;
  private int minimumOrderQuantity;
  private Meta meta;
  private List<String> images;
  private String thumbnail;
}

@Data
class Dimensions {
  private double width;
  private double height;
  private double depth;
}

@Data
class Review {
  private int rating;
  private String comment;
  private Instant date;
  private String reviewerName;
  private String reviewerEmail;
}

@Data
class Meta {
  private Instant createdAt;
  private Instant updatedAt;
  private String barcode;
  private String qrCode;
}
