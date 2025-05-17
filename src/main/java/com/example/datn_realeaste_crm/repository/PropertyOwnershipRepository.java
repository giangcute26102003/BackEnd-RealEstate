package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyOwnershipRepository extends JpaRepository<PropertyOwnership, Integer> {
    
    List<PropertyOwnership> findByUserUserId(Integer userId);
    
    List<PropertyOwnership> findByPropertyPropertyId(Integer propertyId);
    
    Optional<PropertyOwnership> findByUserUserIdAndPropertyPropertyIdAndOwnershipTypeIn(
            Integer userId, Integer propertyId, List<String> ownershipTypes);
}