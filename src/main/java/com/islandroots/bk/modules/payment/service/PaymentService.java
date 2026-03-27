package com.islandroots.bk.modules.payment.service;

import com.islandroots.bk.modules.payment.entity.Payment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentService {
    Payment save(Payment entity);
    Optional<Payment> findById(UUID id);
    List<Payment> findAll();
    void deleteById(UUID id);
}
