package com.toy.ecommerce.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원 관련 오류 1000~
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "1001", "이미 가입된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "1002", "일치하는 회원이 없습니다."),

    // 상품 관련 오류 2000~
    NOT_EXIST_PRODUCT(HttpStatus.BAD_REQUEST, "2001", "존재하지 않는 상품 입니다."),
    NOT_EXIST_OPTION(HttpStatus.BAD_REQUEST, "2002", "존재하지 않는 상품 옵션 입니다."),
    NOT_ENOUGH_INVENTORY(HttpStatus.BAD_REQUEST, "2003", "재고가 부족합니다."),

    NOT_EXIST_CATEGORY(HttpStatus.BAD_REQUEST, "2050", "존재하지 않는 상품 카테고리 입니다."),
    ALREADY_ADDED_CATEGORY(HttpStatus.BAD_REQUEST, "2051", "이미 등록된 상품 카테고리 입니다."),

    // 주문 관련 오류 3000~
    NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "3010", "포인트가 부족합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String detail;
}
