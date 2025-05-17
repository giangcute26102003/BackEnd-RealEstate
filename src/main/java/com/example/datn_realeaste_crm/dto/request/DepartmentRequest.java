package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    private Integer managerId;
}