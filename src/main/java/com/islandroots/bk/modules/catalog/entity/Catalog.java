package com.islandroots.bk.modules.catalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.islandroots.bk.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "catalogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "catalog_products",
        joinColumns = @JoinColumn(name = "catalog_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties("catalogs") // Prevent infinite recursion during JSON serialization
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();
}
