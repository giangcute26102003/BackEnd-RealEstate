package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.dto.request.ReviewRequest;
import com.example.datn_realeaste_crm.dto.response.ReviewResponse;
import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.entity.Review;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;
import com.example.datn_realeaste_crm.repository.PropertyRepository;
import com.example.datn_realeaste_crm.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    
    public Page<ReviewResponse> getAllReviews(Integer propertyId, Pageable pageable) {
        Page<Review> reviews;
        
        if (propertyId != null) {
            reviews = reviewRepository.findByPropertyPropertyId(propertyId, pageable);
        } else {
            reviews = reviewRepository.findAll(pageable);
        }
        
        return reviews.map(this::convertToReviewResponse);
    }
    
    public ReviewResponse getReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        return convertToReviewResponse(review);
    }
    
    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
        
        Review review = new Review();
        review.setUser(currentUser);
        review.setProperty(property);
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        
        Review savedReview = reviewRepository.save(review);
        
        return convertToReviewResponse(savedReview);
    }
    
    @Transactional
    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        
        reviewRepository.deleteById(id);
    }
    
    private ReviewResponse convertToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getReviewId())
                .userId(review.getUser().getUserId())
                .userName(review.getUser().getName())
                .propertyId(review.getProperty().getPropertyId())
                .propertyAddress(review.getProperty().getAddressProperty())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}