package com.toy.ecommerce.product.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CheckOptionForm;
import com.toy.ecommerce.product.entity.ProductOptionInventory;
import com.toy.ecommerce.product.repository.ProductOptionInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductOptionInventoryServiceTest {

    @InjectMocks
    private ProductOptionInventoryService productOptionInventoryService;

    @Mock
    private ProductOptionInventoryRepository productOptionInventoryRepository;


    @Test
    void deductInventory() {

        // given
        int inventory = 10;
        int deductQuantity = 3;
        int expectResultInventory = inventory - deductQuantity;
        ProductOptionInventory mockOptionInventory
                = new ProductOptionInventory("MOCK_OPTION_CODE", null, inventory);
        given(productOptionInventoryRepository.findById(mockOptionInventory.getOptionCode())).willReturn(Optional.of(mockOptionInventory));

        // when
        productOptionInventoryService.deductInventory(mockOptionInventory.getOptionCode(), deductQuantity);

        // then
        verify(productOptionInventoryRepository).save(mockOptionInventory);
        assertEquals(mockOptionInventory.getInventory(), expectResultInventory);
    }

    @Test
    void testDeductInventory_success() {

        // given
        String optionCode1 = "MOCK_OPTION_CODE_1";
        String optionCode2 = "MOCK_OPTION_CODE_2";
        ProductOptionInventory mockOptionInventory1
                = new ProductOptionInventory(optionCode1, null, 10);
        ProductOptionInventory mockOptionInventory2
                = new ProductOptionInventory(optionCode2, null, 10);

        int deductQuantity1 = 5;
        int deductQuantity2 = 3;
        List<CheckOptionForm> mockForms = List.of(
                new CheckOptionForm(optionCode1, deductQuantity1),
                new CheckOptionForm(optionCode2, deductQuantity2));

        int expectResultInventory1 = mockOptionInventory1.getInventory() - deductQuantity1;
        int expectResultInventory2 = mockOptionInventory2.getInventory() - deductQuantity2;

        given(productOptionInventoryRepository.findById(optionCode1)).willReturn(Optional.of(mockOptionInventory1));
        given(productOptionInventoryRepository.findById(optionCode2)).willReturn(Optional.of(mockOptionInventory2));

        // when
        productOptionInventoryService.deductInventory(mockForms);

        // then
        verify(productOptionInventoryRepository).save(mockOptionInventory1);
        verify(productOptionInventoryRepository).save(mockOptionInventory2);
        assertEquals(mockOptionInventory1.getInventory(), expectResultInventory1);
        assertEquals(mockOptionInventory2.getInventory(), expectResultInventory2);
    }

    @Test
    void testDeductInventory_Exception_NotEnoughInventory() {

        // given
        String optionCode = "MOCK_OPTION_CODE";
        ProductOptionInventory mockOptionInventory
                = new ProductOptionInventory(optionCode, null, 10);
        List<CheckOptionForm> mockForms = List.of(new CheckOptionForm(optionCode, 99));
        given(productOptionInventoryRepository.findById(optionCode)).willReturn(Optional.of(mockOptionInventory));

        // when, then
        CustomException customException
                = assertThrows(CustomException.class, () -> productOptionInventoryService.deductInventory(mockForms));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_ENOUGH_INVENTORY);
    }
}