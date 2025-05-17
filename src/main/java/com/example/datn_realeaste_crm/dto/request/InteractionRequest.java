package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InteractionRequest {
    
    @NotNull(message = "Customer ID is required")
    private Integer customerId;
    
    @NotNull(message = "Property ID is required")
    private Integer propertyId;
    
    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Interaction date cannot be in the future")
    private LocalDate date;
    
    private String details;
}