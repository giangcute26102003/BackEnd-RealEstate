package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequirementsRepository extends JpaRepository<CustomerRequirements, Integer> {
    
    Page<CustomerRequirements> findByCustomerCustomerId(Integer customerId, Pageable pageable);
}