package com.toy.ecommercecommon.global.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseResult {

    SUCCESS("성공"),
    ERROR("에러 발생")
    ;

    final String title;
}
