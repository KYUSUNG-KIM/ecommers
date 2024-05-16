package com.toy.product.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.dto.CreateProductForm;
import com.toy.product.dto.ProductListDto;
import com.toy.product.dto.SearchProductCondition;
import com.toy.product.dto.UpdateProductForm;
import com.toy.product.entity.CategoryProduct;
import com.toy.product.entity.Product;
import com.toy.product.repository.ProductDynamicRepository;
import com.toy.product.repository.ProductRepository;
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

        return productRepository.save(product.update(categoryProduct, form));
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
