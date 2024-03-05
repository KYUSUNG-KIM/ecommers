package com.toy.ecommerce.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRoleId implements Serializable {

    private static final long serialVersionUID = -360312474542639967L;

    private long customerId;

    private String role;

}
