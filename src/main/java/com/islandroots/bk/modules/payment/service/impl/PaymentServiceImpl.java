package com.islandroots.bk.modules.payment.service.impl;

import com.islandroots.bk.modules.payment.entity.Payment;
import com.islandroots.bk.modules.payment.repository.PaymentRepository;
import com.islandroots.bk.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public Payment save(Payment entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
