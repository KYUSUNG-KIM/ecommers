package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateSubCategoryForm extends CreateTopCategoryForm {

    @NotNull
    private String parentCategoryCode;

    @NotNull
    private String categoryCode;

    @NotNull
    private String categoryName;
}
