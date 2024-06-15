package com.toy.product;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.constants.SellStatus;
import com.toy.product.dto.CheckOptionForm;
import com.toy.product.dto.CreateOptionForm;
import com.toy.product.entity.Product;
import com.toy.product.entity.ProductOption;
import com.toy.product.entity.ProductOptionInventory;
import com.toy.product.repository.ProductOptionRepository;
import com.toy.product.service.ProductOptionService;
import com.toy.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {

    @InjectMocks
    private ProductOptionService productOptionService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductOptionRepository productOptionRepository;


    @Test
    void create() {

        // given
        Long mockMemberId = 1L;
        String mockProductCode = "MOCK_PRODUCT_CODE";
        CreateOptionForm mockForm = setCreateOptionForm();
        given(productService.getByMemberIdAndProductCode(anyLong(), anyString())).willReturn(Mockito.mock(Product.class));
        given(productOptionRepository.save(any(ProductOption.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ProductOption productOption = productOptionService.create(mockMemberId, mockProductCode, mockForm);

        // then
        assertNotNull(productOption);
        assertNotNull(productOption.getOptionCode());
        assertEquals(productOption.getOptionName(), mockForm.getOptionName());
        assertEquals(productOption.getPrice(), mockForm.getPrice());
        assertEquals(productOption.getOptionInventory().getInventory(), mockForm.getInventory());
    }

    @Test
    void checkPurchasable_true() {

        // given
        CheckOptionForm mockForm = new CheckOptionForm("MOCK_OPTION_CODE", 1);
        List<CheckOptionForm> mockForms = List.of(mockForm);
        ProductOption mockProductOption = setProductOption(mockForm.optionCode(), SellStatus.ON_SALE, 999);
        given(productOptionRepository.findByOptionCodeAndSellStatus(anyString(), any(SellStatus.class)))
                .willReturn(Optional.of(mockProductOption));

        // when
        boolean checkPurchasable = productOptionService.checkPurchasable(mockForms);

        // then
        assertTrue(checkPurchasable);
    }

    @Test
    void checkPurchasable_false() {

        // given
        CheckOptionForm mockForm = new CheckOptionForm("MOCK_OPTION_CODE", 999);
        List<CheckOptionForm> mockForms = List.of(mockForm);
        ProductOption mockProductOption = setProductOption(mockForm.optionCode(), SellStatus.ON_SALE, 1);
        given(productOptionRepository.findByOptionCodeAndSellStatus(anyString(), any(SellStatus.class)))
                .willReturn(Optional.of(mockProductOption));

        // when
        boolean checkPurchasable = productOptionService.checkPurchasable(mockForms);

        // then
        assertFalse(checkPurchasable);
    }

    @Test
    void checkPurchasable_Exception_NotExistOptionCode() {

        // given
        List<CheckOptionForm> mockForms = List.of(new CheckOptionForm("MOCK_OPTION_CODE", 1));
        given(productOptionRepository.findByOptionCodeAndSellStatus(anyString(), any(SellStatus.class))).willReturn(Optional.empty());

        // when, then
        CustomException customException
                = assertThrows(CustomException.class, () -> productOptionService.checkPurchasable(mockForms));
        assertEquals(customException.getErrorCode(), ErrorCode.NOT_EXIST_OPTION);
    }

    private CreateOptionForm setCreateOptionForm() {

        return CreateOptionForm.builder()
                .optionName("MOCK_OPTION_NAME")
                .price(1000)
                .inventory(999)
                .build();
    }

    private ProductOption setProductOption(String optionCode, SellStatus sellStatus, int inventory) {

        return ProductOption.builder()
                .optionCode(optionCode)
                .optionName("MOCK_OPTION_NAME")
                .product(mock(Product.class))
                .sellStatus(sellStatus)
                .price(10000)
                .optionInventory(ProductOptionInventory.of(optionCode, inventory))
                .build();
    }
}
