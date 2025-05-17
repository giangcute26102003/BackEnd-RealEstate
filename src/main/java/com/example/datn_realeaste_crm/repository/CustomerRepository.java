package com.example.datn_realeaste_crm.repository;


import com.example.datn_realeaste_crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    boolean existsByEmail(String email);
    
    boolean existsByPhoneNumber(String phoneNumber);
}