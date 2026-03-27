package com.islandroots.bk.modules.order.controller;

import com.islandroots.bk.modules.order.entity.Order;
import com.islandroots.bk.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order saved = service.save(order);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable UUID id, @RequestBody Order order) {
        return service.findById(id)
                .map(existing -> {
                    // Update properties here based on request body
                    // Assuming id remains the same
                    order.setId(id);
                    return ResponseEntity.ok(service.save(order));
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
