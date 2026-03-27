package com.islandroots.bk.modules.cart.service.impl;

import com.islandroots.bk.modules.cart.entity.Cart;
import com.islandroots.bk.modules.cart.repository.CartRepository;
import com.islandroots.bk.modules.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    @Override
    public Cart save(Cart entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
