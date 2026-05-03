package com.islandroots.bk.modules.shipping.service.impl;

import com.islandroots.bk.modules.order.entity.Order;
import com.islandroots.bk.modules.order.repository.OrderRepository;
import com.islandroots.bk.modules.shipping.dto.LabelResponseDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRateDto;
import com.islandroots.bk.modules.shipping.dto.ShippingRequestDto;
import com.islandroots.bk.modules.shipping.entity.Shipment;
import com.islandroots.bk.modules.shipping.service.ShipmentService;
import com.islandroots.bk.modules.shipping.service.ShippingIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippoIntegrationServiceImpl implements ShippingIntegrationService {

    @Value("${shippo.api-key}")
    private String shippoApiKey;

    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ShippingRateDto> getRates(ShippingRequestDto request) {
        log.info("Fetching real shipping rates from Shippo for destination: {}, weight: {}g", request.getCountry(), request.getTotalWeightGrams());
        
        List<ShippingRateDto> rates = new ArrayList<>();
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "ShippoToken " + shippoApiKey);
            headers.set("Content-Type", "application/json");

            Map<String, Object> addressTo = new HashMap<>();
            addressTo.put("name", "Customer");
            addressTo.put("street1", request.getAddressLine1() != null ? request.getAddressLine1() : "123 Test St");
            addressTo.put("city", request.getCity() != null ? request.getCity() : "Miami");
            addressTo.put("state", request.getState() != null ? request.getState() : "FL");
            addressTo.put("zip", request.getZipCode() != null ? request.getZipCode() : "33101");
            addressTo.put("country", request.getCountry() != null ? request.getCountry() : "US");

            Map<String, Object> addressFrom = new HashMap<>();
            addressFrom.put("name", "Island Roots");
            addressFrom.put("street1", "123 Botanical Gardens");
            addressFrom.put("city", "Kingston");
            addressFrom.put("state", "Kingston");
            addressFrom.put("zip", "JMAAW03");
            addressFrom.put("country", "JM");

            Map<String, Object> parcel = new HashMap<>();
            parcel.put("length", "10");
            parcel.put("width", "10");
            parcel.put("height", "10");
            parcel.put("distance_unit", "in");
            parcel.put("weight", String.valueOf(request.getTotalWeightGrams() > 0 ? request.getTotalWeightGrams() : 500));
            parcel.put("mass_unit", "g");

            Map<String, Object> payload = new HashMap<>();
            payload.put("address_to", addressTo);
            payload.put("address_from", addressFrom);
            payload.put("parcels", List.of(parcel));
            payload.put("async", false);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.goshippo.com/shipments/",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> shippoRates = (List<Map<String, Object>>) response.getBody().get("rates");
                if (shippoRates != null) {
                    for (Map<String, Object> rate : shippoRates) {
                        rates.add(ShippingRateDto.builder()
                                .provider((String) rate.get("provider"))
                                .serviceLevel((String) rate.get("servicelevel_name"))
                                .amount(Double.valueOf(rate.get("amount").toString()))
                                .currency((String) rate.get("currency"))
                                .estimatedDays(rate.get("estimated_days") != null ? rate.get("estimated_days") + " days" : "Unknown")
                                .rateId((String) rate.get("object_id"))
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Shippo API error: {}", e.getMessage());
        }
        
        // Fallback to mock rates if Shippo fails or returns empty rates
        if (rates.isEmpty()) {
            boolean isJamaica = "JM".equalsIgnoreCase(request.getCountry()) || "Jamaica".equalsIgnoreCase(request.getCountry());
            if (isJamaica) {
                rates.add(ShippingRateDto.builder().provider("Jamaica Post").serviceLevel("Local Delivery").amount(5.00).currency("USD").estimatedDays("1-2 days").rateId("mock").build());
            } else {
                rates.add(ShippingRateDto.builder().provider("DHL Express").serviceLevel("Worldwide Express").amount(25.00).currency("USD").estimatedDays("3-5 days").rateId("mock").build());
            }
        }
        
        return rates;
    }

    @Override
    public LabelResponseDto generateLabel(UUID orderId) {
        log.info("Generating shipping label for order: {}", orderId);
        
        // Check if order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        
        // Real implementation would use RestTemplate to POST to https://api.goshippo.com/transactions/
        // using the rateId associated with the order.
        
        String mockTrackingNumber = "TRK" + System.currentTimeMillis() + "JM";
        String mockLabelUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"; // Dummy PDF for testing
        
        // Save to shipment table
        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setTrackingNumber(mockTrackingNumber);
        shipment.setLabelUrl(mockLabelUrl);
        shipmentService.save(shipment);
        
        // Update order status
        order.setStatus(com.islandroots.bk.modules.order.entity.OrderStatus.SHIPPED);
        orderRepository.save(order);
        
        return LabelResponseDto.builder()
                .carrier("DHL")
                .trackingNumber(mockTrackingNumber)
                .trackingUrl("https://dhl.com/tracking?id=" + mockTrackingNumber)
                .labelUrl(mockLabelUrl)
                .build();
    }
}
