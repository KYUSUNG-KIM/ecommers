package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.dto.CheckOptionForm;
import com.toy.ecommerce.product.dto.CreateOptionForm;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.entity.ProductOption;
import com.toy.ecommerce.product.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Optional<ProductOption> getByOptionCode(String optionCode) {

        return productOptionRepository.findById(optionCode);
    }

    // 상품 옵션 생성
    @Transactional
    public ProductOption create(Long sellerId, String productCode, CreateOptionForm form) {

        Product product = productService.getBySellerIdAndProductCode(sellerId, productCode);

        ProductOption productOption = ProductOption.create(product, form);

        return productOptionRepository.save(productOption);
    }


    // 옵션 상품 '재고' 및 '판매 상태' 체크
    @Transactional(readOnly = true)
    public boolean checkPurchasable(List<CheckOptionForm> forms) {

        return forms.stream()
                .allMatch(form -> productOptionRepository.findByOptionCodeAndSellStatus(form.getOptionCode(), SellStatus.ON_SALE)
                        .map(productOption -> hasEnoughInventory(productOption, form.getQuantity()))
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION)));
    }

    private boolean hasEnoughInventory(final ProductOption productOption, final int quantity) {

        return productOption.getOptionInventory().getInventory() >= quantity;
    }

}

















