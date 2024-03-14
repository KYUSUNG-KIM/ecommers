package com.toy.ecommerce.product.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.product.dto.ProductDto;
import com.toy.ecommerce.product.dto.ProductListDto;
import com.toy.ecommerce.product.dto.SearchProductCondition;
import com.toy.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;


    // 상품 조회
    @GetMapping(value = "/products/{productCode}")
    public CommonResponse<ProductDto> getProduct(@PathVariable(name = "productCode") String productCode) {

        return new CommonResponse<>(ProductDto.from(productService.getByProductCode(productCode)));
    }

    // 상품 목록 검색
    @GetMapping(value = "/products/search")
    public CommonResponse<Page<ProductListDto>> searchProducts(SearchProductCondition condition,
                                                               Pageable pageable) {

        return new CommonResponse<>(productService.searchProduct(condition, pageable));
    }


}
