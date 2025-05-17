package com.example.datn_realeaste_crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class InteractionResponse {
    
    private Integer id;
    private Integer customerId;
    private String customerName;
    private Integer propertyId;
    private String propertyAddress;
    private String propertyType;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private String details;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}