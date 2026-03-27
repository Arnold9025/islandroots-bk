package com.islandroots.bk.modules.cart.controller;

import com.islandroots.bk.modules.cart.entity.Cart;
import com.islandroots.bk.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        Cart saved = service.save(cart);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> update(@PathVariable UUID id, @RequestBody Cart cart) {
        return service.findById(id)
                .map(existing -> {
                    // Update properties here based on request body
                    // Assuming id remains the same
                    cart.setId(id);
                    return ResponseEntity.ok(service.save(cart));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return service.findById(id)
                .map(existing -> {
                    service.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
