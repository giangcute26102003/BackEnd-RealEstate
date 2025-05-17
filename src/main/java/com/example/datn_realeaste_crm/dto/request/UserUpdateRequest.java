package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;
    
    @Email(message = "Email must be valid")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    
    private String phoneNumber;
    
    private String address;
    
    private LocalDate dob;
    
    private Integer departmentId;
}