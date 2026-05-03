package com.islandroots.bk.modules.shipping.controller;

import com.islandroots.bk.modules.shipping.entity.Shipment;
import com.islandroots.bk.modules.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.islandroots.bk.modules.shipping.dto.LabelResponseDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRateDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRequestDto;
import com.islandroots.bk.modules.shipping.service.ShippingIntegrationService;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService service;
    private final ShippingIntegrationService integrationService;

    @PostMapping("/rates")
    public ResponseEntity<List<ShippingRateDto>> getRates(@RequestBody ShippingRequestDto request) {
        return ResponseEntity.ok(integrationService.getRates(request));
    }

    @PostMapping("/{orderId}/label")
    public ResponseEntity<LabelResponseDto> generateLabel(@PathVariable UUID orderId) {
        return ResponseEntity.ok(integrationService.generateLabel(orderId));
    }

    @PostMapping
    public ResponseEntity<com.islandroots.bk.modules.shipping.entity.Shipment> create(@RequestBody com.islandroots.bk.modules.shipping.entity.Shipment shipment) {
        com.islandroots.bk.modules.shipping.entity.Shipment saved = service.save(shipment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
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
