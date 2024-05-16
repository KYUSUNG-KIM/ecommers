package com.toy.product.dto;

import com.toy.product.entity.ProductOption;
import lombok.Builder;

public record ProductOptionFeignResponse(String optionCode,
                                         String optionName,
                                         String sellStatus,
                                         int price,
                                         int inventory,
                                         String productCode,
                                         String productName) {

    @Builder public ProductOptionFeignResponse {}

    public static ProductOptionFeignResponse from(ProductOption option) {
        return ProductOptionFeignResponse.builder()
                .optionCode(option.getOptionCode())
                .optionName(option.getOptionName())
                .sellStatus(option.getSellStatus().getCode())
                .price(option.getPrice())
                .inventory(option.getOptionInventory().getInventory())
                .productCode(option.getProduct().getProductCode())
                .productName(option.getProduct().getName())
                .build();
    }
}
