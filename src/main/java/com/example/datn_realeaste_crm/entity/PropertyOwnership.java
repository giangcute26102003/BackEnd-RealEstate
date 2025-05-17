package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "property_ownership")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyOwnership {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownership_id")
    private Integer ownershipId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @Column(name = "ownership_type")
    @Enumerated(EnumType.STRING)
    private OwnershipType ownershipType = OwnershipType.owner;
    
    @Column(name = "ownership_percentage")
    private BigDecimal ownershipPercentage = new BigDecimal("100.00");
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum OwnershipType {
        owner,
        co_owner("co-owner"),
        agent;
        
        private final String value;
        
        OwnershipType() {
            this.value = this.name();
        }
        
        OwnershipType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static OwnershipType fromValue(String value) {
            for (OwnershipType type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid ownership type: " + value);
        }
    }
}