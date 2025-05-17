package com.example.datn_realeaste_crm.service;


import com.example.datn_realeaste_crm.entity.Property;
import com.example.datn_realeaste_crm.entity.PropertyOwnership;
import com.example.datn_realeaste_crm.entity.User;
import com.example.datn_realeaste_crm.repository.PropertyOwnershipRepository;
import com.example.datn_realeaste_crm.repository.PropertyRepository;
import com.example.datn_realeaste_crm.repository.UserPropertyAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PropertyAuthorizationService {
    
    private final PropertyRepository propertyRepository;
    private final PropertyOwnershipRepository propertyOwnershipRepository;
    private final UserPropertyAccessRepository userPropertyAccessRepository;
    
    public boolean isPropertyOwner(Authentication authentication, Integer propertyId) {
        if (!(authentication.getPrincipal() instanceof User)) {
            return false;
        }
        
        User user = (User) authentication.getPrincipal();
        
        // Check if user is admin
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        
        // Check if user is the owner
        Optional<PropertyOwnership> ownership = propertyOwnershipRepository
                .findByUserUserIdAndPropertyPropertyIdAndOwnershipTypeIn(
                        user.getUserId(), 
                        propertyId, 
                        java.util.List.of("owner", "co-owner")
                );
        
        return ownership.isPresent();
    }
    
    public boolean canAccessProperty(Authentication authentication, Integer propertyId) {
        if (!(authentication.getPrincipal() instanceof User)) {
            return false;
        }
        
        User user = (User) authentication.getPrincipal();
        
        // Check if user is admin or has property_view permission
        if (user.getAuthorities().stream().anyMatch(a -> 
                a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("property_view"))) {
            return true;
        }
        
        // Check if user is Manager and property belongs to their department
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            Optional<Property> property = propertyRepository.findById(propertyId);
            if (property.isPresent() && property.get().getDepartment() != null && 
                    user.getDepartment() != null && 
                    property.get().getDepartment().getDepartmentId().equals(user.getDepartment().getDepartmentId())) {
                return true;
            }
        }
        
        // Check if user is Consultant with access
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CONSULTANT"))) {
            return userPropertyAccessRepository.existsByUserUserIdAndPropertyPropertyId(user.getUserId(), propertyId);
        }
        
        // Check if user is the owner
        return isPropertyOwner(authentication, propertyId);
    }
}