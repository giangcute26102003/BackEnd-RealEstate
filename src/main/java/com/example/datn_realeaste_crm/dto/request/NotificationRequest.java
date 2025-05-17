package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {
    
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    @NotBlank(message = "Message is required")
    private String message;
}