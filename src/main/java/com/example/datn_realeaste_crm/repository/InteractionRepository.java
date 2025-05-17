package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Integer> {
    
    Page<Interaction> findByCustomerCustomerId(Integer customerId, Pageable pageable);
    
    Page<Interaction> findByPropertyPropertyId(Integer propertyId, Pageable pageable);
    
    Page<Interaction> findByCustomerCustomerIdAndPropertyPropertyId(Integer customerId, Integer propertyId, Pageable pageable);
}