package com.islandroots.bk.modules.shipping.repository;

import com.islandroots.bk.modules.shipping.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
}
