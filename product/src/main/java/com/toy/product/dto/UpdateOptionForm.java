package com.toy.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOptionForm {

    @NotNull
    private String optionCode;

    @NotNull
    @Size(max = 100)
    private String optionName;

    @NotNull
    @Min(1)
    private Integer price;

    @NotNull
    @Min(0)
    private Integer inventory;

    @NotNull
    private String sellStatus;

}
