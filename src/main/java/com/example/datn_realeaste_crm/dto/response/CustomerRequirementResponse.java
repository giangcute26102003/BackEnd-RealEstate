package com.example.datn_realeaste_crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerRequirementResponse {
    
    private Integer id;
    private Integer customerId;
    private String customerName;
    private String purpose;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String preferredLocation;
    private String propertyType;
    private BigDecimal sizeMin;
    private Integer bedrooms;
    private Integer bathrooms;
    private String otherPreferences;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}