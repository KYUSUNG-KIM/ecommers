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
    private String optionCode;

    @NotNull
    @Size(max = 100)
    private String optionName;

    @Min(1)
    private Integer price;

    @Min(0)
    private Integer inventory;

    @NotNull
    private String sellStatus;

}
