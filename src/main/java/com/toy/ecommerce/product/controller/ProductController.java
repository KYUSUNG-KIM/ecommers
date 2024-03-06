package com.toy.ecommerce.product.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.product.dto.CreateProductForm;
import com.toy.ecommerce.product.dto.ProductDto;
import com.toy.ecommerce.product.dto.UpdateProductForm;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.service.ProductManagementService;
import com.toy.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final ProductManagementService productManagementService;


    @PostMapping(value = "/product")
    public CommonResponse createProduct(@RequestBody CreateProductForm form) {

        // todo : seller ID 입력 받기
        Product product = productManagementService.createProduct(1L, form);

        return new CommonResponse(ProductDto.from(product));
    }


    @PutMapping(value = "/product/{productCode}")
    public CommonResponse updateProduct(@PathVariable(name = "productCode") String productCode,
                                        @RequestBody UpdateProductForm form) {

        // todo : seller ID 입력 받기
        Product product = productManagementService.updateProduct(1L, productCode, form);

        return new CommonResponse(ProductDto.from(product));
    }



    @GetMapping(value = "/product/{productCode}")
    public CommonResponse getProducts(@PathVariable(name = "productCode") String productCode) {

        ProductDto productDto = productService.getInfoByProductCode(productCode);

        return new CommonResponse(productDto);
    }


}
