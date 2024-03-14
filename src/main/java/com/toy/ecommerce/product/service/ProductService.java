package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.*;
import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.repository.ProductDynamicRepository;
import com.toy.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CategoryProductService categoryProductService;

    private final ProductRepository productRepository;
    private final ProductDynamicRepository productDynamicRepository;


    // 상품 등록
    @Transactional
    public Product create(Long sellerId, CreateProductForm form) {

        CategoryProduct categoryProduct = categoryProductService.getByCategoryCode(form.getCategoryCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CATEGORY));

        Product product = Product.create(sellerId, categoryProduct, form);

        return productRepository.save(product);
    }

    // 상품 수정
    @Transactional
    public Product update(Long sellerId, String productCode, UpdateProductForm form) {

        Product product = productRepository.findBySellerIdAndProductCode(sellerId, productCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));

        CategoryProduct categoryProduct = categoryProductService.getByCategoryCode(form.getCategoryCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CATEGORY));

        return productRepository.save(Product.update(product, categoryProduct, form));
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public Product getByProductCode(String productCode) {

        return productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public Product getBySellerIdAndProductCode(Long sellerId, String productCode) {

        return productRepository.findBySellerIdAndProductCode(sellerId, productCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public Page<ProductListDto> searchProduct(SearchProductCondition condition, Pageable pageable) {

        return productDynamicRepository.searchProducts(condition, pageable)
                .map(ProductListDto::from);
    }

}
