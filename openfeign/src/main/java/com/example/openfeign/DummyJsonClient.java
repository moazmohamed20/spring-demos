package com.example.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openfeign.model.Page;
import com.example.openfeign.model.Product;

@FeignClient(name = "DummyJson", url = "https://dummyjson.com")
public interface DummyJsonClient {

  @GetMapping("/products")
  public Page getAllProducts();

  @GetMapping("/products/{id}")
  public Product getProductById(@PathVariable int id);

  @GetMapping("/products/search")
  public Page searchProducts(@RequestParam String q);

}
