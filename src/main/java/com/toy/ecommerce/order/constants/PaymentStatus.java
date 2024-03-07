package com.toy.ecommerce.order.constants;

import com.toy.ecommerce.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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