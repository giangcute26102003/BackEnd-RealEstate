package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @Column(name = "image_url")
    private String imageUrl;
}