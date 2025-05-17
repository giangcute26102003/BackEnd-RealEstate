package com.example.datn_realeaste_crm.repository;


import com.example.datn_realeaste_crm.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    
    District findByName(String name);
}