package com.toy.ecommerce.product.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.product.dto.CategoryProductDto;
import com.toy.ecommerce.product.dto.CreateCategoryForm;
import com.toy.ecommerce.product.service.CategoryProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryProductController {

    private final CategoryProductService categoryProductService;


    // 카테고리 등록
    @PostMapping("/category/products")
    public CommonResponse createCategory(@Valid @RequestBody CreateCategoryForm form) {

        categoryProductService.create(form);

        return new CommonResponse("OK");
    }


    // 전체 카테고리 조회
    @GetMapping("/category/products")
    public CommonResponse getAllCategories() {

        List<CategoryProductDto> dtoList = categoryProductService.getAllCategories();

        return new CommonResponse(dtoList);
    }
}
