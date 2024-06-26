package com.toy.product.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.ecommercecommon.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SearchKeywordType implements EnumProperty {

    PRODUCT_CODE("상품 코드"),
    PRODUCT_NAME("상품명"),
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


    public static SearchKeywordType fromValue(String value) {
        try{
            return valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }
}
