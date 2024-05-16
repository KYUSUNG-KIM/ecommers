package com.toy.product.dto;

import com.toy.product.constants.SellStatus;
import com.toy.product.entity.ProductOption;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionDto {

    private String optionCode;

    private String optionName;

    private SellStatus sellStatus;

    private Integer price;

    private Integer inventory;


    public static ProductOptionDto from(ProductOption option) {

        return ProductOptionDto.builder()
                .optionCode(option.getOptionCode())
                .optionName(option.getOptionName())
                .sellStatus(option.getSellStatus())
                .price(option.getPrice())
                .inventory(option.getOptionInventory().getInventory())
                .build();
    }
}
