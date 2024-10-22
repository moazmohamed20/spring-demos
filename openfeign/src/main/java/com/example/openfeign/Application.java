package com.example.openfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.openfeign.model.Page;
import com.example.openfeign.model.Product;

@EnableFeignClients
@SpringBootApplication
public class Application implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  DummyJsonClient dummyJsonClient;

  public void run(ApplicationArguments args) {
    Product productById = dummyJsonClient.getProductById(1);
    System.out.println(productById);

    Page allProducts = dummyJsonClient.getAllProducts();
    System.out.println(allProducts);

    Page searchProducts = dummyJsonClient.searchProducts("dummy");
    System.out.println(searchProducts);
  }

}
