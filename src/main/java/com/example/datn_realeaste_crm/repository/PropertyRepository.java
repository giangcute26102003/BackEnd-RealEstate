package com.example.datn_realeaste_crm.repository;


import com.example.datn_realeaste_crm.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer>, JpaSpecificationExecutor<Property> {

    @Query("SELECT p FROM Property p WHERE p.availability = 'APPROVED'")
    Page<Property> findAllApproved(Pageable pageable);

    @Query("SELECT p FROM Property p WHERE p.availability = 'PENDING'")
    Page<Property> findAllPending(Pageable pageable);

    @Query("SELECT p FROM Property p WHERE p.availability = 'REJECTED'")
    Page<Property> findAllRejected(Pageable pageable);

    Page<Property> findByUser_UserId(Integer userId, Pageable pageable);

    Page<Property> findByDepartment_DepartmentId(Integer departmentId, Pageable pageable);

    List<Property> findByPropertyTypeAndDistrictIdAndBedroomsGreaterThanEqual(String propertyType, Integer districtId, Integer bedrooms);

    long countByAvailability(String availability);

    long countByUserUserId(Integer userId);

    long countByUserUserIdAndAvailability(Integer userId, String availability);

    @Query("SELECT AVG(p.price) FROM Property p WHERE p.availability = 'APPROVED'")
    BigDecimal findAveragePrice();
}