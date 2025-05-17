package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;
    
    @Column(name = "permission_name", nullable = false, length = 100)
    private String permissionName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "category", length = 100)
    private String category;
    
    @Column(name = "resource", length = 100)
    private String resource;
    
    @Column(name = "action", length = 100)
    private String action;
}