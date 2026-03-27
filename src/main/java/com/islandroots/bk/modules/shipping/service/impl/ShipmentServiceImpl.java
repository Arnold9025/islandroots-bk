package com.islandroots.bk.modules.shipping.service.impl;

import com.islandroots.bk.modules.shipping.entity.Shipment;
import com.islandroots.bk.modules.shipping.repository.ShipmentRepository;
import com.islandroots.bk.modules.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository repository;

    @Override
    public Shipment save(Shipment entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shipment> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
