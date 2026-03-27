package com.islandroots.bk.modules.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/orders/{id}/label")
    public ResponseEntity<String> printLabel(@PathVariable UUID id) {
        // TODO: Fetch Shippo URL from Shipment module 
        return ResponseEntity.ok("Print label endpoint stub for order " + id);
    }

    @GetMapping("/analytics")
    public ResponseEntity<String> getAnalytics() {
        // TODO: Implement analytics aggregate view
        return ResponseEntity.ok("Analytics endpoint stub");
    }
}
