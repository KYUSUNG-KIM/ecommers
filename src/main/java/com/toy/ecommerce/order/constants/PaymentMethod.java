package com.toy.ecommerce.order.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.ecommerce.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentMethod implements EnumProperty {

//    VIRTUAL_ACCOUNT("가상 계좌"),
//    CARD("카드"),
//    DEPOSIT("무통장 입금"),
    POINT("포인트")
    ;

    private String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
