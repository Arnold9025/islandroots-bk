package com.islandroots.bk.modules.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 1024)
    private String imageUrl;

    private String author;

    @Builder.Default
    private LocalDateTime publishedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BlogStatus status = BlogStatus.PUBLISHED;
}

