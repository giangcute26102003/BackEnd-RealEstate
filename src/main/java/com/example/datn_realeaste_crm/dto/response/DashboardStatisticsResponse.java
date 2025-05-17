package com.example.datn_realeaste_crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardStatisticsResponse {
    
    private long totalProperties;
    private long pendingProperties;
    private long approvedProperties;
    private long rejectedProperties;
    
    private long totalUsers;
    private long activeUsers;
    
    private long totalCustomers;
    private long totalInteractions;
    
    private BigDecimal averagePropertyPrice;
}