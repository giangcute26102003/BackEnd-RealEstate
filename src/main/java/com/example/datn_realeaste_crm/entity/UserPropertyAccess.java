package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_property_access")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPropertyAccess {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @Column(name = "access_granted_at")
    private LocalDateTime accessGrantedAt;
}