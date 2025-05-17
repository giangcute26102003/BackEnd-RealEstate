
package com.example.datn_realeaste_crm.entity;

import com.example.datn_realeaste_crm.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_requirements")
@Getter
@Setter
public class CustomerRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Integer requirementId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "purpose", columnDefinition = "TEXT")
    private String purpose;
    
    @Column(name = "budget_min")
    private BigDecimal budgetMin;
    
    @Column(name = "budget_max")
    private BigDecimal budgetMax;
    
    @Column(name = "preferred_location", columnDefinition = "TEXT")
    private String preferredLocation;
    
    @Column(name = "property_type")
    private String propertyType;
    
    @Column(name = "size_min")
    private BigDecimal sizeMin;
    
    @Column(name = "bedrooms")
    private Integer bedrooms;
    
    @Column(name = "bathrooms")
    private Integer bathrooms;
    
    @Column(name = "other_preferences", columnDefinition = "TEXT")
    private String otherPreferences;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}