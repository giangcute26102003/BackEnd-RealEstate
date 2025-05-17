package com.example.datn_realeaste_crm.audit;


import com.example.datn_realeaste_crm.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    
    Page<AuditLog> findByUser_UserId(Integer userId, Pageable pageable);
    
    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, Integer entityId, Pageable pageable);
    
    Page<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    Page<AuditLog> findByDepartment_DepartmentId(Integer departmentId, Pageable pageable);
}

