package com.toy.ecommerce.product.dto;

import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.entity.ProductOption;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionDto {

    private String optionCode;          // 옵션 코드, PK

    private String optionName;          // 옵션명

    private SellStatus sellStatus;      // 판매 상태

    private Integer extraAmount;        // 추가 금액

    private Integer inventory;          // 재고


    public static ProductOptionDto from(ProductOption option) {

        return ProductOptionDto.builder()
                .optionCode(option.getOptionCode())
                .optionName(option.getOptionName())
                .sellStatus(option.getSellStatus())
                .extraAmount(option.getExtraAmount())
                .inventory(option.getOptionInventory().getInventory())
                .build();
    }
}
