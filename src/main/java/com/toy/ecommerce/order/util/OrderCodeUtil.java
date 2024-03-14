package com.toy.ecommerce.order.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class OrderCodeUtil {

    private static final String dateTimeFormat = "yyMMddHHmmss";


    /*
    주문 코드 : 생성일자(yyMMddHHmmss) + 랜덤코드 6자리
     */
    public static String generateOrderCode() {

        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormat, Locale.KOREA));

        String randomCodePart = RandomStringUtils.random(6, true, true);

        return datePart + randomCodePart;
    }
}
