package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Integer> {
    
    List<PropertyImage> findByPropertyPropertyId(Integer propertyId);
    
    void deleteByPropertyPropertyId(Integer propertyId);
}