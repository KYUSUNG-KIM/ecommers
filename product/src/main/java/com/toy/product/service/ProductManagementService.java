package com.toy.product.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.dto.CreateOptionForm;
import com.toy.product.dto.CreateProductForm;
import com.toy.product.dto.UpdateProductForm;
import com.toy.product.entity.Product;
import com.toy.product.entity.ProductOption;
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
    public Product createProduct(Long memberId, CreateProductForm form) {

        Product newProduct = productService.create(memberId, form);

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

            productOptionService.save(productOption.update(optionForm));
        });

        return updateProduct;
    }

}
