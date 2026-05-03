package com.islandroots.bk.modules.payment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CheckoutRequest {
    private BigDecimal amount;
    private UUID orderId;
    private String successUrl;
    private String cancelUrl;
}
