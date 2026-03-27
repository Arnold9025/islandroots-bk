package com.islandroots.bk.modules.shipping.controller;

import com.islandroots.bk.modules.shipping.entity.Shipment;
import com.islandroots.bk.modules.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService service;

    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody Shipment shipment) {
        Shipment saved = service.save(shipment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> update(@PathVariable UUID id, @RequestBody Shipment shipment) {
        return service.findById(id)
                .map(existing -> {
                    // Update properties here based on request body
                    // Assuming id remains the same
                    shipment.setId(id);
                    return ResponseEntity.ok(service.save(shipment));
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
