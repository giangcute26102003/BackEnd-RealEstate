package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(RolePermissionId.class)
public class RolePermission {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}