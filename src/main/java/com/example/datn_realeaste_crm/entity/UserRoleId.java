package com.example.datn_realeaste_crm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleId implements Serializable {
    
    private Integer user;
    private Integer role;
}