package com.islandroots.bk.modules.shipping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelResponseDto {
    private String labelUrl;
    private String trackingNumber;
    private String trackingUrl;
    private String carrier;
}
