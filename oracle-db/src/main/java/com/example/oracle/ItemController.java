package com.example.oracle;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {

  private final ItemRepository itemRepository;

  // GET ALL ITEMS
  @GetMapping("/items")
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  // GET ITEM BY ID
  @GetMapping("/items/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    return ResponseEntity.of(itemRepository.findById(id));
  }

  // DELETE ITEM BY ID
  @DeleteMapping("/items/{id}")
  public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
    if (!itemRepository.existsById(id))
      return ResponseEntity.notFound().build();
    itemRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  // CREATE ITEM
  @PostMapping("/items")
  public Item createItem(@RequestBody Item item) {
    return itemRepository.save(item);
  }
}