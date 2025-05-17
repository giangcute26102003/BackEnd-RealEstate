package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PropertyOwnershipRequest {
    
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    @NotNull(message = "Property ID is required")
    private Integer propertyId;
    
    private String ownershipType = "owner";
    
    private BigDecimal ownershipPercentage = new BigDecimal("100.00");
    
    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate startDate;
    
    private LocalDate endDate;
}