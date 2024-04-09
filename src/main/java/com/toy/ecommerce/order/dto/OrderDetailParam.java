package com.toy.ecommerce.order.dto;

import com.toy.ecommerce.product.entity.ProductOption;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailParam {

    private String optionCode;

    private String productName;

    private Integer quantity;

    private Integer price;

    public static OrderDetailParam from(ProductOption option, int quantity) {

        return OrderDetailParam.builder()
                .optionCode(option.getOptionCode())
                .productName(option.productAndOptionName())
                .price(option.getPrice())
                .quantity(quantity)
                .build();
    }
}
