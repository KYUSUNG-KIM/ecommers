package com.toy.ecommerce.product.dto;

import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.entity.Product;
import lombok.*;

/*
상품 목록 조회 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListDto {

    private String productCode;                 // 상품 코드

    private CategoryProductSimpleDto category;  // 상품 카테고리

    private String name;                        // 상품명

    private String subName;                     // 서브 이름

    private String description;                 // 설명

    private SellStatus sellStatus;              // 판매 상태


    public static ProductListDto from(Product product) {

        return ProductListDto.builder()
                .productCode(product.getProductCode())
                .category(CategoryProductSimpleDto.from(product.getCategoryProduct()))
                .name(product.getName())
                .subName(product.getSubName())
                .description(product.getDescription())
                .sellStatus(product.getSellStatus())
                .build();
    }
}
