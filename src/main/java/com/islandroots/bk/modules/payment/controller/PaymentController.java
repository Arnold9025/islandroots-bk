package com.islandroots.bk.modules.payment.controller;

import com.islandroots.bk.modules.payment.entity.Payment;
import com.islandroots.bk.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        Payment saved = service.save(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(@PathVariable UUID id, @RequestBody Payment payment) {
        return service.findById(id)
                .map(existing -> {
                    // Update properties here based on request body
                    // Assuming id remains the same
                    payment.setId(id);
                    return ResponseEntity.ok(service.save(payment));
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
