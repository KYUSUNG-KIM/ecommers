package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryForm {

    @NotNull
    private Boolean hasParentCategory;

    private String parentCategoryCode;

    @NotNull
    private String categoryCode;

    @NotNull
    private String categoryName;
}
