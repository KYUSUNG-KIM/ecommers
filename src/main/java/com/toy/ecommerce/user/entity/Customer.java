package com.toy.ecommerce.user.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String customerName;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int balance;

    @NotNull
    private String verificationCode;

    @NotNull
    private boolean verify = false;

    @NotNull
    private LocalDateTime verifyExpiredAt;

    @NotNull
    private boolean isValid = true;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId", updatable = false)
    private List<CustomerRole> roles;
}
