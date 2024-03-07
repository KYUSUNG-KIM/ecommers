package com.toy.ecommerce.product.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.product.dto.ProductDto;
import com.toy.ecommerce.product.dto.ProductListDto;
import com.toy.ecommerce.product.dto.SearchProductCondition;
import com.toy.ecommerce.product.entity.Product;
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
    public CommonResponse getProduct(@PathVariable(name = "productCode") String productCode) {

        Product product = productService.getByProductCode(productCode);

        return new CommonResponse(ProductDto.from(product));
    }

    // 상품 목록 검색
    @GetMapping(value = "/products/search")
    public CommonResponse searchProducts(SearchProductCondition condition,
                                         Pageable pageable) {

        Page<ProductListDto> dtoList = productService.searchProduct(condition, pageable)
                .map(ProductListDto::from);

        return new CommonResponse(dtoList);
    }


}
