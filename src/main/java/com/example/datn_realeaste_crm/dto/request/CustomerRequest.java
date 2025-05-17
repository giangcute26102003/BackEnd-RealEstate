package com.example.datn_realeaste_crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;
    
    private String phoneNumber;
    
    @Email(message = "Email must be valid")
    private String email;
    
    private String address;
    
    private LocalDate dob;
}