package com.example.datn_realeaste_crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PropertyOwnershipResponse {
    
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer propertyId;
    private String propertyAddress;
    private String ownershipType;
    private BigDecimal ownershipPercentage;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}