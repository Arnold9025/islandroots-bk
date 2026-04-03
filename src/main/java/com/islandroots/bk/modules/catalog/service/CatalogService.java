package com.islandroots.bk.modules.catalog.service;

import com.islandroots.bk.modules.catalog.entity.Catalog;

import java.util.List;
import java.util.UUID;

public interface CatalogService {
    Catalog createCatalog(Catalog catalog);
    Catalog getCatalogById(UUID id);
    List<Catalog> getAllCatalogs();
    Catalog updateCatalog(UUID id, Catalog catalogDetails);
    void deleteCatalog(UUID id);
    Catalog addProductToCatalog(UUID catalogId, UUID productId);
    Catalog removeProductFromCatalog(UUID catalogId, UUID productId);
}
