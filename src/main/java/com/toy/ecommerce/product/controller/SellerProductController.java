package com.toy.ecommerce.product.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.product.dto.CreateProductForm;
import com.toy.ecommerce.product.dto.ProductDto;
import com.toy.ecommerce.product.dto.UpdateProductForm;
import com.toy.ecommerce.product.service.ProductManagementService;
import com.toy.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
판매자 상품 관리 API
 */
@RestController
@RequiredArgsConstructor
public class SellerProductController {


    private final ProductService productService;
    private final ProductManagementService productManagementService;


    // 상품 등록
    @PostMapping(value = "/seller/products")
    public CommonResponse<ProductDto> createProduct(@RequestBody CreateProductForm form) {

        // todo : seller ID 입력 받기

        return new CommonResponse<>(ProductDto.from(productManagementService.createProduct(1L, form)));
    }

    // 상품 수정
    @PutMapping(value = "/seller/products/{productCode}")
    public CommonResponse<ProductDto> updateProduct(@PathVariable(name = "productCode") String productCode,
                                                    @Valid @RequestBody UpdateProductForm form) {

        // todo : seller ID 입력 받기

        return new CommonResponse<>(ProductDto.from(productManagementService.updateProduct(1L, productCode, form)));
    }
}
