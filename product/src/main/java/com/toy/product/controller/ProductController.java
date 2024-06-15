package com.toy.product.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.product.dto.*;
import com.toy.product.service.ProductManagementService;
import com.toy.product.service.ProductOptionInventoryService;
import com.toy.product.service.ProductOptionService;
import com.toy.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final ProductOptionInventoryService productOptionInventoryService;
    private final ProductManagementService productManagementService;


    // 상품 등록
    @PostMapping(value = "/products")
    public CommonResponse<ProductDto> createProduct(@RequestBody CreateProductForm form) {

        // TODO
        return new CommonResponse<>(ProductDto.from(productManagementService.createProduct(1L, form)));
    }

    // 상품 수정
    @PutMapping(value = "/products/{productCode}")
    public CommonResponse<ProductDto> updateProduct(@PathVariable(name = "productCode") String productCode,
                                                    @Valid @RequestBody UpdateProductForm form) {

        // TODO
        return new CommonResponse<>(ProductDto.from(productManagementService.updateProduct(1L, productCode, form)));
    }

    // 상품 목록 검색
    @GetMapping(value = "/products")
    public CommonResponse<Page<ProductListDto>> searchProducts(SearchProductCondition condition,
                                                               Pageable pageable) {

        return new CommonResponse<>(productService.searchProduct(condition, pageable));
    }

    // 상품 조회
    @GetMapping(value = "/products/{productCode}")
    public CommonResponse<ProductDto> getProduct(@PathVariable(name = "productCode") String productCode) {

        return new CommonResponse<>(ProductDto.from(productService.getByProductCode(productCode)));
    }

    // 옵션 정보 조회
    // 한번에 조회되도록 변경
    @Deprecated
    @GetMapping(value = "/options/{optionCode}")
    public ProductOptionFeignResponse getProductOption(@PathVariable("optionCode") String optionCode) {

        return productOptionService.getOptionByOptionCode(optionCode);
    }

    // 옵션 구매 가능 여부 조회
    @PostMapping(value = "/options/purchasable")
    public boolean checkPurchasableOfProductOption(@RequestBody List<CheckOptionForm> forms) {

        return productOptionService.checkPurchasable(forms);
    }

    // 인벤토리 수량 감소
    @PostMapping(value = "/options/inventories/deduct")
    public void deductInventory(@RequestBody List<CheckOptionForm> forms) {

        productOptionInventoryService.deductInventory(forms);
    }

}
