package com.toy.ecommerce.product.service;

import com.toy.ecommerce.product.dto.CreateOptionForm;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.entity.ProductOption;
import com.toy.ecommerce.product.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOptionService {

    private final ProductService productService;

    private final ProductOptionRepository productOptionRepository;


    @Transactional
    public ProductOption save(ProductOption option) {

        return productOptionRepository.save(option);
    }

    // 상품 옵션 생성
    @Transactional
    public ProductOption create(Long sellerId, String productCode, CreateOptionForm form) {

        Product product = productService.getBySellerIdAndProductCode(sellerId, productCode);

        ProductOption productOption = ProductOption.create(product, form);

        return productOptionRepository.save(productOption);
    }

}

















