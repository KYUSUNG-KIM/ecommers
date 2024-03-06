package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CreateProductForm;
import com.toy.ecommerce.product.dto.ProductDto;
import com.toy.ecommerce.product.dto.UpdateProductForm;
import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CategoryProductService categoryProductService;

    private final ProductRepository productRepository;


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
    public ProductDto getInfoByProductCode(String productCode) {

        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));

        return ProductDto.from(product);
    }


    // 상품 조회
    @Transactional(readOnly = true)
    public Optional<Product> getBySellerIdAndProductCode(Long sellerId, String productCode) {

        return productRepository.findBySellerIdAndProductCode(sellerId, productCode);
    }

}
