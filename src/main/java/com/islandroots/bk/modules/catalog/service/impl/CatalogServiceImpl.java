package com.islandroots.bk.modules.catalog.service.impl;

import com.islandroots.bk.modules.catalog.entity.Catalog;
import com.islandroots.bk.modules.catalog.repository.CatalogRepository;
import com.islandroots.bk.modules.catalog.service.CatalogService;
import com.islandroots.bk.modules.product.entity.Product;
import com.islandroots.bk.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;

    @Override
    public Catalog createCatalog(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public Catalog getCatalogById(UUID id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));
    }

    @Override
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @Override
    public Catalog updateCatalog(UUID id, Catalog catalogDetails) {
        Catalog catalog = getCatalogById(id);
        catalog.setName(catalogDetails.getName());
        catalog.setDescription(catalogDetails.getDescription());
        return catalogRepository.save(catalog);
    }

    @Override
    public void deleteCatalog(UUID id) {
        Catalog catalog = getCatalogById(id);
        catalogRepository.delete(catalog);
    }

    @Override
    @Transactional
    public Catalog addProductToCatalog(UUID catalogId, UUID productId) {
        Catalog catalog = getCatalogById(catalogId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!catalog.getProducts().contains(product)) {
            catalog.getProducts().add(product);
            product.getCatalogs().add(catalog);
            catalogRepository.save(catalog);
        }
        return catalog;
    }

    @Override
    @Transactional
    public Catalog removeProductFromCatalog(UUID catalogId, UUID productId) {
        Catalog catalog = getCatalogById(catalogId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (catalog.getProducts().contains(product)) {
            catalog.getProducts().remove(product);
            product.getCatalogs().remove(catalog);
            catalogRepository.save(catalog);
        }
        return catalog;
    }
}
