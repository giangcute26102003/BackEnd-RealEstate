package com.example.datn_realeaste_crm.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatisticsResponse {
    
    private long myProperties;
    private long myApprovedProperties;
    private long myPendingProperties;
    
    private long myFavorites;
    private long myReviews;
    private long unreadNotifications;
}