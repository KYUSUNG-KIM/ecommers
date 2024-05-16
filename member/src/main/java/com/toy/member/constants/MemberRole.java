package com.toy.member.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    CUSTOMER("고객", "ROLE_CUSTOMER"),
    SELLER("판매자", "ROLE_SELLER"),
    CUSTOMER_SELLER("고객 겸 판매자", "ROLE_CUSTOMER,ROLE_SELLER")
    ;

    private final String title;
    private final String authorities;
}
