package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CategoryProductDto;
import com.toy.ecommerce.product.dto.CreateCategoryForm;
import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.repository.CategoryProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryProductService {

    private final CategoryProductRepository categoryProductRepository;


    @Transactional(readOnly = true)
    public Optional<CategoryProduct> getByCategoryCode(String categoryCode) {

        return categoryProductRepository.findByCategoryCode(categoryCode);
    }

    // 전체 카테고리 정보 조회
    @Transactional(readOnly = true)
    public List<CategoryProductDto> getAllCategories() {

        return categoryProductRepository.findAllByParentNull()
                .stream()
                .map(CategoryProductDto::from)
                .toList();
    }


    /*
    상품 카테고리 생성
    - 최상위 카테고리 CategoryProduct.parent = null
     */
    @Transactional
    public CategoryProduct create(CreateCategoryForm form) {

        if (categoryProductRepository.existsByCategoryCode(form.getCategoryCode())) {
            throw new CustomException(ErrorCode.ALREADY_ADDED_CATEGORY);
        }

        CategoryProduct parent = null;

        if (form.getHasParentCategory()) {
            parent = categoryProductRepository.findByCategoryCode(form.getParentCategoryCode())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CATEGORY));
        }

        return categoryProductRepository.save(CategoryProduct.of(parent, form));
    }

}
