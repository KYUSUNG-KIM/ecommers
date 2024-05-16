package com.toy.product.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.ecommercecommon.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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

    public static SellStatus fromValue(String code) {
        try {
            return valueOf(code);
        }
        catch (Exception e) {
            return null;
        }
    }
}
