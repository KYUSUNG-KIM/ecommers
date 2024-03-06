package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOptionForm {

    @NotNull
    private String optionCode;              // 옵션 코드

    @NotNull
    @Size(max = 100)
    private String optionName;              // 옵션명

    @Min(0)
    private Integer extraAmount;            // 추가 금액

    @Min(0)
    private Integer inventory;              // 재고

    @NotNull
    private String sellStatus;          // 판매 상태

}
