package com.islandroots.bk.modules.product.service;

import com.islandroots.bk.modules.product.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product save(Product entity);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    void deleteById(UUID id);
}
