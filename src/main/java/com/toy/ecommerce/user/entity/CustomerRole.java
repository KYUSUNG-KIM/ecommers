package com.toy.ecommerce.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "ecc_customer_role")
@Getter
@Setter
@IdClass(value = CustomerRoleId.class)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRole {

    @Id
    private long customerId;

    @Id
    private String role;

}
