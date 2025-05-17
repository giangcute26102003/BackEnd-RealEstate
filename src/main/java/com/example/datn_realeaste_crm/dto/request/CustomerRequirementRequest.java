package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerRequirementRequest {
    
    @NotNull(message = "Customer ID is required")
    private Integer customerId;
    
    private String purpose;
    
    private BigDecimal budgetMin;
    
    private BigDecimal budgetMax;
    
    private String preferredLocation;
    
    private String propertyType;
    
    private BigDecimal sizeMin;
    
    private Integer bedrooms;
    
    private Integer bathrooms;
    
    private String otherPreferences;
}