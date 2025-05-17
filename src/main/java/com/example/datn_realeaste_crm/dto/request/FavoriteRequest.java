package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequest {
    
    @NotNull(message = "Property ID is required")
    private Integer propertyId;
}