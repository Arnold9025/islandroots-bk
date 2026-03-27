package com.islandroots.bk.modules.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Table(name = "orderitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID productId;
    private int quantity;
    private BigDecimal price;
}
