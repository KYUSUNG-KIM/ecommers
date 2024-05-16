package com.toy.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
