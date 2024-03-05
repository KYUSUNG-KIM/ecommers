package com.toy.ecommerce.user.dto;

import com.toy.ecommerce.user.entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String email;

    @JsonProperty("name")
    private String customerName;

    private String phoneNumber;

    private int balance;

    public static CustomerDto of(Customer customer) {
        return CustomerDto.builder()
                .email(customer.getEmail())
                .customerName(customer.getCustomerName())
                .phoneNumber(customer.getPhoneNumber())
                .balance(customer.getBalance())
                .build();
    }
}
