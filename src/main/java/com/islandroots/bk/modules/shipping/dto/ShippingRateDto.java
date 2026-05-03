package com.islandroots.bk.modules.shipping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRateDto {
    private String provider; // e.g. "DHL Express", "Jamaica Post"
    private String serviceLevel; // e.g. "Standard", "Express"
    private Double amount;
    private String currency;
    private String estimatedDays;
    private String rateId; // Use this to purchase the label later
}
