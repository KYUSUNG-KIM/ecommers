package com.toy.ecommerce.product.dto;

import com.toy.ecommerce.product.entity.CategoryProduct;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryProductSimpleDto {
    private String categoryCode;
    private String categoryName;

    public static CategoryProductSimpleDto from(CategoryProduct categoryProduct) {

        return CategoryProductSimpleDto.builder()
                .categoryCode(categoryProduct.getCategoryCode())
                .categoryName(categoryProduct.getCategoryName())
                .build();
    }
}
