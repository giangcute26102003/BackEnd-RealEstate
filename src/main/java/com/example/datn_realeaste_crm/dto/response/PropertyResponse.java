package com.example.datn_realeaste_crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PropertyResponse {
    
    private Integer id;
    private String address;
    private String propertyType;
    private BigDecimal size;
    private Integer floor;
    private String thumbnail;
    private Integer bedrooms;
    private Integer bathrooms;
    private String description;
    private BigDecimal price;
    private String legalDocuments;
    private String availability;
    private String phoneOwner;
    private Integer districtId;
    private String districtName;
    private Integer departmentId;
    private String departmentName;
    private Integer userId;
    private String userName;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}