package com.islandroots.bk.modules.shipping.service;

import com.islandroots.bk.modules.shipping.dto.LabelResponseDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRateDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRequestDto;

import java.util.List;
import java.util.UUID;

public interface ShippingIntegrationService {
    List<ShippingRateDto> getRates(ShippingRequestDto request);
    LabelResponseDto generateLabel(UUID orderId);
}
