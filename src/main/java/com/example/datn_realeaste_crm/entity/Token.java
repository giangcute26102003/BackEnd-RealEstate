package com.example.datn_realeaste_crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tokens") // hoặc tên bảng bạn đã tạo trong DB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "revoked")
    private Boolean revoked = false;

    @Column(name = "expired")
    private Boolean expired = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}