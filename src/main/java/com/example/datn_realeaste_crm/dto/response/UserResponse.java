package com.example.datn_realeaste_crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    
    private Boolean isActive;
    private Integer departmentId;
    private String departmentName;
    private Set<String> roles;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}