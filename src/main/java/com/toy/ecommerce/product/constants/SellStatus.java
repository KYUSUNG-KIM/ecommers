package com.toy.ecommerce.product.constants;

import com.toy.ecommerce.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum SellStatus implements EnumProperty {

    ON_SALE("판매중"),
    STOP_SALE("판매 중지"),
    WAITING_SALE("판매 대기"),
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
