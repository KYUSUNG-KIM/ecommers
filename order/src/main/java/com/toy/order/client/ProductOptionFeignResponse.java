package com.toy.order.client;

import lombok.Builder;

public record ProductOptionFeignResponse(String optionCode,
                                         String optionName,
                                         String sellStatus,
                                         int price,
                                         int inventory,
                                         String productCode,
                                         String productName) {

    public String productAndOptionName() {
        return this.productName + " " + this.optionName;
    }

    @Builder
    public ProductOptionFeignResponse {}
}
