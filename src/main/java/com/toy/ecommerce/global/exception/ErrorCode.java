package com.toy.ecommerce.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "1001", "이미 가입된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "1002", "일치하는 회원이 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String detail;
}
