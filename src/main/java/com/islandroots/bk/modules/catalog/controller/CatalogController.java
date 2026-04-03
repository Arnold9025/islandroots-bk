package com.islandroots.bk.modules.catalog.controller;

import com.islandroots.bk.modules.catalog.entity.Catalog;
import com.islandroots.bk.modules.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping
    public ResponseEntity<Catalog> create(@RequestBody Catalog catalog) {
        Catalog saved = catalogService.createCatalog(catalog);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Catalog> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogService.getCatalogById(id));
    }

    @GetMapping
    public ResponseEntity<List<Catalog>> getAll() {
        return ResponseEntity.ok(catalogService.getAllCatalogs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Catalog> update(@PathVariable UUID id, @RequestBody Catalog catalog) {
        return ResponseEntity.ok(catalogService.updateCatalog(id, catalog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        catalogService.deleteCatalog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{catalogId}/products/{productId}")
    public ResponseEntity<Catalog> addProduct(@PathVariable UUID catalogId, @PathVariable UUID productId) {
        return ResponseEntity.ok(catalogService.addProductToCatalog(catalogId, productId));
    }

    @DeleteMapping("/{catalogId}/products/{productId}")
    public ResponseEntity<Catalog> removeProduct(@PathVariable UUID catalogId, @PathVariable UUID productId) {
        return ResponseEntity.ok(catalogService.removeProductFromCatalog(catalogId, productId));
    }
}
