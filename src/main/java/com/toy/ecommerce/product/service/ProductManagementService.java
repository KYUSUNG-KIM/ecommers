package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CreateOptionForm;
import com.toy.ecommerce.product.dto.CreateProductForm;
import com.toy.ecommerce.product.dto.UpdateProductForm;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductManagementService {

    private final ProductService productService;
    private final ProductOptionService productOptionService;


    @Transactional
    public Product createProduct(Long sellerId, CreateProductForm form) {

        Product newProduct = productService.create(sellerId, form);

        for (CreateOptionForm optionForm : form.getOptions()) {
            newProduct.addOption(productOptionService.save(ProductOption.create(newProduct, optionForm)));
        }

        return newProduct;
    }

    @Transactional
    public Product updateProduct(Long sellerId, String productCode, UpdateProductForm form) {

        Product updateProduct = productService.update(sellerId, productCode, form);

        form.getOptions().forEach(optionForm -> {
            ProductOption productOption = updateProduct.getOptions().stream()
                    .filter(option -> StringUtils.equals(option.getOptionCode(), optionForm.getOptionCode()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));

            productOptionService.save(ProductOption.update(productOption, optionForm));
        });

        return updateProduct;
    }

}
