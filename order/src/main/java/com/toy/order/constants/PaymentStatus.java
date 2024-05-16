package com.toy.order.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.ecommercecommon.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentStatus implements EnumProperty {

    PENDING("결제 대기"),
    COMPLETED("결제 완료")
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
