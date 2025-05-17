package com.example.datn_realeaste_crm.repository;

import com.example.datn_realeaste_crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    
    Page<Notification> findByUserUserId(Integer userId, Pageable pageable);
    
    Page<Notification> findByUserUserIdAndStatus(Integer userId, String status, Pageable pageable);
    
    @Modifying
    @Query("UPDATE Notification n SET n.status = :status WHERE n.user.userId = :userId AND n.status = 'Chưa đọc'")
    void updateStatusForUser(@Param("userId") Integer userId, @Param("status") String status);
    
    long countByUserUserIdAndStatus(Integer userId, String status);
}