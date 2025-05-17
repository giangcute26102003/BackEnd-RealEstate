package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewAuthorizationService {
    
    private final ReviewRepository reviewRepository;
    
    public boolean isReviewOwner(Authentication authentication, Integer reviewId) {
        if (!(authentication.getPrincipal() instanceof User)) {
            return false;
        }
        
        User user = (User) authentication.getPrincipal();
        
        // Check if user is admin
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        
        // Check if user is the owner of the review
        return reviewRepository.findById(reviewId)
                .map(review -> review.getUser().getUserId().equals(user.getUserId()))
                .orElse(false);
    }
}