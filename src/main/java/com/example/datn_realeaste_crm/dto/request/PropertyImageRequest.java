package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PropertyImageRequest {
    
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}