package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "district")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class District {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name", length = 50)
    private String name;
}