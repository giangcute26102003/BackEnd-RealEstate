package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPropertyAccessRepository extends JpaRepository<UserPropertyAccess, Integer> {
    
    List<UserPropertyAccess> findByUserUserId(Integer userId);
    
    List<UserPropertyAccess> findByPropertyPropertyId(Integer propertyId);
    
    boolean existsByUserUserIdAndPropertyPropertyId(Integer userId, Integer propertyId);
    
    void deleteByUserUserIdAndPropertyPropertyId(Integer userId, Integer propertyId);
}