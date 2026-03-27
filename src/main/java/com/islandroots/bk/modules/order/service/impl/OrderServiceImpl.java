package com.islandroots.bk.modules.order.service.impl;

import com.islandroots.bk.modules.order.entity.Order;
import com.islandroots.bk.modules.order.repository.OrderRepository;
import com.islandroots.bk.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public Order save(Order entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
