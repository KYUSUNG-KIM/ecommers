package com.toy.ecommerce.product.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProductCodeUtil {

    private static final String productCodeInitialPart = "P";
    private static final String datePattern = "yyMMdd";

    /*
    상품 코드 : 'P' + 생성일자(yyMMdd) + 랜덤코드 6자리
     */
    public static String generateProductCode() {

        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern, Locale.KOREA));

        String randomCodePart = RandomStringUtils.random(6, true, true);

        return productCodeInitialPart + datePart + randomCodePart;
    }


    /*
    옵션 코드 : 상품 코드 + 랜덤코드 4자리
     */
    public static String generateOptionCode(String productCode) {

        String randomCodePart = RandomStringUtils.random(4, true, true);

        return productCode + randomCodePart;
    }
}
