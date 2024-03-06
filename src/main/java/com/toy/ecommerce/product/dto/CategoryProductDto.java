package com.toy.ecommerce.product.dto;

import com.toy.ecommerce.product.entity.CategoryProduct;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryProductDto {

    private String categoryCode;

    private String categoryName;

    private List<CategoryProductDto> children = new ArrayList<>();


    public static CategoryProductDto from(CategoryProduct categoryProduct) {

        return CategoryProductDto.builder()
                .categoryCode(categoryProduct.getCategoryCode())
                .categoryName(categoryProduct.getCategoryName())
                .children(categoryProduct.getChildren()
                        .stream()
                        .map(CategoryProductDto::from)
                        .toList())
                .build();
    }
}
