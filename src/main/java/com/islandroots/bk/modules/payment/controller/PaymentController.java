package com.islandroots.bk.modules.payment.controller;

import com.islandroots.bk.modules.order.service.OrderService;
import com.islandroots.bk.modules.order.entity.Order;
import com.islandroots.bk.modules.order.entity.OrderStatus;
import com.islandroots.bk.modules.payment.entity.Payment;
import com.islandroots.bk.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.islandroots.bk.modules.payment.service.impl.StripeService;
import com.islandroots.bk.modules.payment.dto.CheckoutRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService service;
    private final StripeService stripeService;
    private final OrderService orderService;

    @PostMapping("/checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody CheckoutRequest req) {
        try {
            // Save initial payment with PENDING status
            Payment paymentReq = Payment.builder()
                    .orderId(req.getOrderId())
                    .amount(req.getAmount())
                    .status("PENDING")
                    .build();
            Payment savedPayment = service.save(paymentReq);

            String successUrl = req.getSuccessUrl() != null ? req.getSuccessUrl() : "http://localhost:3000/en/checkout/success";
            String cancelUrl = req.getCancelUrl() != null ? req.getCancelUrl() : "http://localhost:3000/en/cart";

            Session session = stripeService.createCheckoutSession(
                    savedPayment.getOrderId(),
                    savedPayment.getAmount(),
                    "usd",
                    successUrl,
                    cancelUrl
            );

            savedPayment.setStripeSessionId(session.getId());
            service.save(savedPayment);

            Map<String, String> response = new HashMap<>();
            response.put("url", session.getUrl());
            response.put("sessionId", session.getId());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify-session/{sessionId}")
    public ResponseEntity<Payment> verifySession(@PathVariable String sessionId) {
        try {
            Session session = stripeService.retrieveSession(sessionId);
            
            // Find payment by sessionId (in a real app you'd find by ID, but we just get the first matching one)
            Payment payment = service.findAll().stream()
                    .filter(p -> sessionId.equals(p.getStripeSessionId()))
                    .findFirst()
                    .orElse(null);

            if (payment != null) {
                // Store the payment intent ID (the actual stripe payment ID)
                payment.setStripePaymentId(session.getPaymentIntent());
                
                if ("paid".equals(session.getPaymentStatus())) {
                    payment.setStatus("PAID");
                    
                    // Also update the order status
                    orderService.findById(payment.getOrderId()).ifPresent(order -> {
                        order.setStatus(OrderStatus.PAID);
                        orderService.save(order);
                    });
                }
                
                return ResponseEntity.ok(service.save(payment));
            }
            return ResponseEntity.notFound().build();
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        Payment saved = service.save(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(@PathVariable UUID id, @RequestBody Payment payment) {
        return service.findById(id)
                .map(existing -> {
                    payment.setId(id);
                    return ResponseEntity.ok(service.save(payment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return service.findById(id)
                .map(existing -> {
                    service.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
