package com.example.datn_realeaste_crm.service;

import com.example.datn_realeaste_crm.dto.response.*;
import com.example.datn_realeaste_crm.entity.Favorite;
import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.exception.ResourceAlreadyExistsException;
import com.example.datn_realeaste_crm.exception.ResourceNotFoundException;
import com.example.datn_realeaste_crm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    
    public Page<PropertyResponse> getFavoriteProperties(Integer userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        
        return favoriteRepository.findByUserUserId(userId, pageable)
                .map(favorite -> convertToPropertyResponse(favorite.getProperty()));
    }
    
    @Transactional
    public void addFavorite(Integer userId, Integer propertyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
        
        // Check if already favorite
        Optional<Favorite> existingFavorite = favoriteRepository.findByUserUserIdAndPropertyPropertyId(userId, propertyId);
        if (existingFavorite.isPresent()) {
            throw new ResourceAlreadyExistsException("Property is already in favorites");
        }
        
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProperty(property);
        favorite.setCreatedAt(LocalDateTime.now());
        
        favoriteRepository.save(favorite);
    }
    
    @Transactional
    public void removeFavorite(Integer userId, Integer propertyId) {
        Optional<Favorite> favorite = favoriteRepository.findByUserUserIdAndPropertyPropertyId(userId, propertyId);
        
        if (favorite.isEmpty()) {
            throw new ResourceNotFoundException("Favorite not found for user id: " + userId + " and property id: " + propertyId);
        }
        
        favoriteRepository.delete(favorite.get());
    }
    
    public boolean checkFavorite(Integer userId, Integer propertyId) {
        return favoriteRepository.existsByUserUserIdAndPropertyPropertyId(userId, propertyId);
    }
    
    private PropertyResponse convertToPropertyResponse(Property property) {
        return PropertyResponse.builder()
                .id(property.getPropertyId())
                .address(property.getAddressProperty())
                .propertyType(property.getPropertyType())
                .size(property.getSize())
                .floor(property.getFloor())
                .thumbnail(property.getThumbnail())
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .description(property.getDescription())
                .price(property.getPrice())
                .legalDocuments(property.getLegalDocuments())
                .availability(property.getAvailability())
                .phoneOwner(property.getPhoneOwner())
                .districtId(property.getDistrict() != null ? property.getDistrict().getId() : null)
                .districtName(property.getDistrict() != null ? property.getDistrict().getName() : null)
                .departmentId(property.getDepartment() != null ? property.getDepartment().getDepartmentId() : null)
                .departmentName(property.getDepartment() != null ? property.getDepartment().getName() : null)
                .userId(property.getUser() != null ? property.getUser().getUserId() : null)
                .userName(property.getUser() != null ? property.getUser().getName() : null)
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }
}