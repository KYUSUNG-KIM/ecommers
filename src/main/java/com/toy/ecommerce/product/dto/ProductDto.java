package com.toy.ecommerce.product.dto;

import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String productCode;                 // 상품 코드, UK

    private CategoryProductVo category;         // 상품 카테고리, FK

    private String name;                        // 상품명

    private String subName;                     // 서브 이름

    private Integer price;                      // 가격

    private String description;                 // 설명

    private SellStatus sellStatus;              // 판매 상태

    private List<ProductOptionDto> options;     // 상품 옵션



    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CategoryProductVo {
        private String categoryCode;
        private String categoryName;

        public static CategoryProductVo from(CategoryProduct categoryProduct) {

            return CategoryProductVo.builder()
                    .categoryCode(categoryProduct.getCategoryCode())
                    .categoryName(categoryProduct.getCategoryName())
                    .build();
        }
    }


    public static ProductDto from(Product product) {

        return ProductDto.builder()
                .productCode(product.getProductCode())
                .category(CategoryProductVo.from(product.getCategoryProduct()))
                .name(product.getName())
                .subName(product.getSubName())
                .price(product.getPrice())
                .description(product.getDescription())
                .sellStatus(product.getSellStatus())
                .options(product.getOptions().stream().map(ProductOptionDto::from).toList())
                .build();
    }
}
