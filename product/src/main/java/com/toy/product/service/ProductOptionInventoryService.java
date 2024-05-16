package com.toy.product.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.dto.CheckOptionForm;
import com.toy.product.entity.ProductOptionInventory;
import com.toy.product.repository.ProductOptionInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionInventoryService {

    private final ProductOptionInventoryRepository productOptionInventoryRepository;

    @Transactional
    public void deductInventory(String optionCode, int deductQuantity) {

        ProductOptionInventory inventory
                = productOptionInventoryRepository.findById(optionCode)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));

        inventory.deductInventory(deductQuantity);
        productOptionInventoryRepository.save(inventory);
    }

    @Transactional
    public void deductInventory(List<CheckOptionForm> forms) {

        for (CheckOptionForm form : forms) {

            ProductOptionInventory inventory
                    = productOptionInventoryRepository.findById(form.optionCode())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));

            inventory.deductInventory(form.quantity());

            if (inventory.getInventory() < 0) {
                throw new CustomException(ErrorCode.NOT_ENOUGH_INVENTORY);
            }

            productOptionInventoryRepository.save(inventory);
        }
    }

}
