package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOptionForm {

    @NotNull
    @Size(max = 100)
    private String optionName;              // 옵션명

    @Min(0)
    private Integer price;            // 추가 금액

    @Min(0)
    private Integer inventory;              // 재고

}
