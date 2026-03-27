package com.islandroots.bk.modules.cart.service;

import com.islandroots.bk.modules.cart.entity.Cart;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartService {
    Cart save(Cart entity);
    Optional<Cart> findById(UUID id);
    List<Cart> findAll();
    void deleteById(UUID id);
}
