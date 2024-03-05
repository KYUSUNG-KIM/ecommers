package com.toy.ecommerce.user.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    CUSTOMER("고객", "TEST_USER"),
    SELLER("판매자", "ROLE_USER");

    private final String title;
    private final String authorities;
}
