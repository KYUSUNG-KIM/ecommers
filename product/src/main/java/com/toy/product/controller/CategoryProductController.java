package com.toy.product.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.product.dto.CategoryProductDto;
import com.toy.product.dto.CreateSubCategoryForm;
import com.toy.product.dto.CreateTopCategoryForm;
import com.toy.product.service.CategoryProductService;
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


    // TODO 관리자 기능으로 편입시켜야 한다.
    // 최상위 카테고리 등록
    @PostMapping("/category-top/products")
    public CommonResponse<CategoryProductDto> createCategory(@Valid @RequestBody CreateTopCategoryForm form) {

        return new CommonResponse<>(CategoryProductDto.from(categoryProductService.createTopCategory(form)));
    }

    // TODO 관리자 기능으로 편입시켜야 한다.
    // 하위 카테고리 등록
    @PostMapping("/category-sub/products")
    public CommonResponse<CategoryProductDto> createCategory(@Valid @RequestBody CreateSubCategoryForm form) {

        return new CommonResponse<>(CategoryProductDto.from(categoryProductService.createChildCategory(form)));
    }

    // 전체 카테고리 조회
    @GetMapping("/category/products")
    public CommonResponse<List<CategoryProductDto>> getAllCategories() {

        return new CommonResponse<>(categoryProductService.getAllCategories());
    }
}
