package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleAssignmentRequest {
    
    @NotNull(message = "Role ID is required")
    private Integer roleId;
}