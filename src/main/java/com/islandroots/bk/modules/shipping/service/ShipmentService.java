package com.islandroots.bk.modules.shipping.service;

import com.islandroots.bk.modules.shipping.entity.Shipment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentService {
    Shipment save(Shipment entity);
    Optional<Shipment> findById(UUID id);
    List<Shipment> findAll();
    void deleteById(UUID id);
}
