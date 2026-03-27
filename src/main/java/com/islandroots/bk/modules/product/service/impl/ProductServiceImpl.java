package com.islandroots.bk.modules.product.service.impl;

import com.islandroots.bk.modules.product.entity.Product;
import com.islandroots.bk.modules.product.repository.ProductRepository;
import com.islandroots.bk.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product save(Product entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
