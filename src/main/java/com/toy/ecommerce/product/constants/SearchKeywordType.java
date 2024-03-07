package com.toy.ecommerce.product.constants;

import com.toy.ecommerce.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
