package com.islandroots.bk.modules.shipping.dto;

import lombok.Data;

@Data
public class ShippingRequestDto {
    private String addressLine1;
    private String city;
    private String state;
    private String zipCode;
    private String country; // ISO2 code e.g. "US", "JM"
    private Double totalWeightGrams;
}
