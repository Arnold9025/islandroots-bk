package com.islandroots.bk.modules.order.service;

import com.islandroots.bk.modules.order.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order save(Order entity);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    void deleteById(UUID id);
}
