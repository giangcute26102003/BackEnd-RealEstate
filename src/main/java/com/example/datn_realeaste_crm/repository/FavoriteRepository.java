package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    
    Page<Favorite> findByUserUserId(Integer userId, Pageable pageable);
    
    Optional<Favorite> findByUserUserIdAndPropertyPropertyId(Integer userId, Integer propertyId);
    
    boolean existsByUserUserIdAndPropertyPropertyId(Integer userId, Integer propertyId);
    
    void deleteByUserUserIdAndPropertyPropertyId(Integer userId, Integer propertyId);
    long countByUserUserId(Integer userId);
}