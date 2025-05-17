package com.example.datn_realeaste_crm.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditlog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false, columnDefinition = "TEXT")
    private String action;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "previous_value", columnDefinition = "TEXT")
    private String previousValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}