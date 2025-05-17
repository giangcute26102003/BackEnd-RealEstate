package com.example.datn_realeaste_crm.security;

import com.example.datn_realeaste_crm.dto.request.PropertyOwnershipRequest;
import com.example.datn_realeaste_crm.dto.response.PropertyOwnershipResponse;
import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.entity.PropertyOwnership;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.exception.*;
import com.example.datn_realeaste_crm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyOwnershipService {
    
    private final PropertyOwnershipRepository propertyOwnershipRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    
    public List<PropertyOwnershipResponse> getUserOwnerships(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        
        return propertyOwnershipRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToPropertyOwnershipResponse)
                .collect(Collectors.toList());
    }
    
    public List<PropertyOwnershipResponse> getPropertyOwnerships(Integer propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }
        
        return propertyOwnershipRepository.findByPropertyPropertyId(propertyId)
                .stream()
                .map(this::convertToPropertyOwnershipResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PropertyOwnershipResponse addOwnership(PropertyOwnershipRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
        
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
        
        PropertyOwnership ownership = new PropertyOwnership();
        ownership.setUser(user);
        ownership.setProperty(property);
        ownership.setOwnershipType(PropertyOwnership.OwnershipType.valueOf(request.getOwnershipType()));
        ownership.setOwnershipPercentage(request.getOwnershipPercentage());
        ownership.setStartDate(request.getStartDate());
        ownership.setEndDate(request.getEndDate());
        ownership.setCreatedAt(LocalDateTime.now());
        
        PropertyOwnership savedOwnership = propertyOwnershipRepository.save(ownership);
        
        return convertToPropertyOwnershipResponse(savedOwnership);
    }
    
    @Transactional
    public PropertyOwnershipResponse updateOwnership(Integer ownershipId, PropertyOwnershipRequest request) {
        PropertyOwnership ownership = propertyOwnershipRepository.findById(ownershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Property ownership not found with id: " + ownershipId));
        
        if (request.getUserId() != null && !ownership.getUser().getUserId().equals(request.getUserId())) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
            ownership.setUser(user);
        }
        
        if (request.getPropertyId() != null && !ownership.getProperty().getPropertyId().equals(request.getPropertyId())) {
            Property property = propertyRepository.findById(request.getPropertyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
            ownership.setProperty(property);
        }
        
        if (request.getOwnershipType() != null) {
            ownership.setOwnershipType(PropertyOwnership.OwnershipType.valueOf(request.getOwnershipType()));
        }
        
        if (request.getOwnershipPercentage() != null) {
            ownership.setOwnershipPercentage(request.getOwnershipPercentage());
        }
        
        if (request.getStartDate() != null) {
            ownership.setStartDate(request.getStartDate());
        }
        
        if (request.getEndDate() != null) {
            ownership.setEndDate(request.getEndDate());
        }
        
        PropertyOwnership updatedOwnership = propertyOwnershipRepository.save(ownership);
        
        return convertToPropertyOwnershipResponse(updatedOwnership);
    }
    
    @Transactional
    public void deleteOwnership(Integer ownershipId) {
        if (!propertyOwnershipRepository.existsById(ownershipId)) {
            throw new ResourceNotFoundException("Property ownership not found with id: " + ownershipId);
        }
        
        propertyOwnershipRepository.deleteById(ownershipId);
    }
    
    private PropertyOwnershipResponse convertToPropertyOwnershipResponse(PropertyOwnership ownership) {
        return PropertyOwnershipResponse.builder()
                .id(ownership.getOwnershipId())
                .userId(ownership.getUser().getUserId())
                .userName(ownership.getUser().getName())
                .propertyId(ownership.getProperty().getPropertyId())
                .propertyAddress(ownership.getProperty().getAddressProperty())
                .ownershipType(String.valueOf(ownership.getOwnershipType()))
                .ownershipPercentage(ownership.getOwnershipPercentage())
                .startDate(ownership.getStartDate())
                .endDate(ownership.getEndDate())
                .createdAt(ownership.getCreatedAt())
                .build();
    }
}