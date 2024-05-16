package com.toy.order.dto;

import com.toy.order.client.ProductOptionFeignResponse;
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

    public static OrderDetailParam of(ProductOptionFeignResponse option, int quantity) {

        return OrderDetailParam.builder()
                .optionCode(option.optionCode())
                .productName(option.productAndOptionName())
                .price(option.price())
                .quantity(quantity)
                .build();
    }
}
