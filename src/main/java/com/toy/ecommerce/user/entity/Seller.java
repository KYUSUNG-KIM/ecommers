package com.toy.ecommerce.user.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ecc_seller")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private long sellerId;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String verificationCode;

    @NotNull
    private boolean verify = false;

    @NotNull
    private LocalDateTime verifyExpiredAt;

    @NotNull
    private boolean isValid = true;
}
