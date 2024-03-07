package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CheckOptionForm;
import com.toy.ecommerce.product.entity.ProductOptionInventory;
import com.toy.ecommerce.product.repository.ProductOptionInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionInventoryService {

    private final ProductOptionInventoryRepository productOptionInventoryRepository;

    @Transactional
    public void deductInventory(List<CheckOptionForm> forms) {

        forms.forEach(form -> {
            ProductOptionInventory inventory
                    = productOptionInventoryRepository.findById(form.getOptionCode())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_PRODUCT));

            inventory.deductInventory(form.getQuantity());
            productOptionInventoryRepository.save(inventory);
        });
    }
}
