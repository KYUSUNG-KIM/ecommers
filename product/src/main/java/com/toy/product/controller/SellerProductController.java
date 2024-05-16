package com.toy.product.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.product.dto.CreateProductForm;
import com.toy.product.dto.ProductDto;
import com.toy.product.dto.UpdateProductForm;
import com.toy.product.service.ProductManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
판매자 상품 관리 API
 */
@RestController
@RequiredArgsConstructor
public class SellerProductController {


    private final ProductManagementService productManagementService;


    // 상품 등록
    @PostMapping(value = "/seller/products")
    public CommonResponse<ProductDto> createProduct(@RequestBody CreateProductForm form) {

        return new CommonResponse<>(ProductDto.from(productManagementService.createProduct(1L, form)));
    }

    // 상품 수정
    @PutMapping(value = "/seller/products/{productCode}")
    public CommonResponse<ProductDto> updateProduct(@PathVariable(name = "productCode") String productCode,
                                                    @Valid @RequestBody UpdateProductForm form) {

        return new CommonResponse<>(ProductDto.from(productManagementService.updateProduct(1L, productCode, form)));
    }
}
