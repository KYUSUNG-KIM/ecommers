package com.toy.product.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.constants.SellStatus;
import com.toy.product.dto.CheckOptionForm;
import com.toy.product.dto.CreateOptionForm;
import com.toy.product.dto.ProductOptionFeignResponse;
import com.toy.product.entity.Product;
import com.toy.product.entity.ProductOption;
import com.toy.product.repository.ProductOptionRepository;
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
                .allMatch(form -> productOptionRepository.findByOptionCodeAndSellStatus(form.optionCode(), SellStatus.ON_SALE)
                        .map(productOption -> hasEnoughInventory(productOption, form.quantity()))
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION)));
    }

    private boolean hasEnoughInventory(final ProductOption productOption, final int quantity) {

        return productOption.getOptionInventory().getInventory() >= quantity;
    }

    @Transactional(readOnly = true)
    public ProductOptionFeignResponse getOptionByOptionCode(final String optionCode) {
        ProductOption productOption = productOptionRepository.findByOptionCode(optionCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));

        return ProductOptionFeignResponse.from(productOption);
    }
}

















