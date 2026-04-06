package com.islandroots.bk.modules.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.islandroots.bk.modules.catalog.entity.Catalog;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_preparations", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "preparation", columnDefinition = "TEXT")
    @Builder.Default
    private List<String> preparations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_ingredients", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "ingredient", columnDefinition = "TEXT")
    @Builder.Default
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_vertus", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "vertu", columnDefinition = "TEXT")
    @Builder.Default
    private List<String> vertus = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_poids", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "poids")
    @Builder.Default
    private List<String> poids = new ArrayList<>(List.of("2g", "50g"));

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties("products")
    @Builder.Default
    private List<Catalog> catalogs = new ArrayList<>();
}
