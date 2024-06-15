package com.toy.product;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.product.constants.SellStatus;
import com.toy.product.dto.CreateProductForm;
import com.toy.product.dto.UpdateProductForm;
import com.toy.product.entity.CategoryProduct;
import com.toy.product.entity.Product;
import com.toy.product.repository.ProductDynamicRepository;
import com.toy.product.repository.ProductRepository;
import com.toy.product.service.CategoryProductService;
import com.toy.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryProductService categoryProductService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDynamicRepository productDynamicRepository;

    private final String mockProductCode = "PRODUCT_CODE";
    private final Long mockMemberId = 1L;
    private final String mockCategoryCode = "CATEGORY_CODE";


    @Test
    void create() {

        // given
        CreateProductForm mockForm = setMockCreateProductForm();
        CategoryProduct mockCategoryProduct = setMockCategoryProduct(mockForm.getCategoryCode());
        given(categoryProductService.getByCategoryCode(anyString())).willReturn(Optional.of(mockCategoryProduct));
        given(productRepository.save(any(Product.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Product product = productService.create(mockMemberId, mockForm);

        // then
        assertNotNull(product);
        assertNotNull(product.getProductCode());
        assertEquals(product.getName(), mockForm.getName());
        assertEquals(product.getSubName(), mockForm.getSubName());
        assertEquals(product.getDescription(), mockForm.getDescription());
        assertEquals(product.getCategoryProduct().getCategoryCode(), mockForm.getCategoryCode());
        assertEquals(product.getMemberId(), mockMemberId);
        assertFalse(product.isDelete());
    }

    @Test
    void update() {

        // given
        UpdateProductForm mockForm = setMockUpdateProductForm();
        Product mockProduct = setMockProduct(mockProductCode, mockMemberId);
        CategoryProduct mockCategoryProduct = setMockCategoryProduct(mockForm.getCategoryCode());
        given(productRepository.findByMemberIdAndProductCode(mockMemberId, mockProductCode)).willReturn(Optional.of(mockProduct));
        given(categoryProductService.getByCategoryCode(mockForm.getCategoryCode())).willReturn(Optional.of(mockCategoryProduct));
        given(productRepository.save(any(Product.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Product updateProduct = productService.update(mockMemberId, mockProductCode, mockForm);

        // then
        assertNotNull(updateProduct);
        assertEquals(updateProduct.getProductCode(), mockProductCode);
        assertEquals(updateProduct.getMemberId(), mockMemberId);
        assertEquals(updateProduct.getName(), mockForm.getName());
        assertEquals(updateProduct.getSubName(), mockForm.getSubName());
        assertEquals(updateProduct.getDescription(), mockForm.getDescription());
        assertEquals(updateProduct.getCategoryProduct().getCategoryCode(), mockForm.getCategoryCode());
        assertEquals(updateProduct.getSellStatus().getCode(), mockForm.getSellStatus());
    }

    @Test
    void getByProductCode() {

        //given
        Product mockProduct = setMockProduct(mockProductCode, mockMemberId);
        given(productRepository.findByProductCode(mockProductCode)).willReturn(Optional.of(mockProduct));

        //when
        Product product = productService.getByProductCode(mockProductCode);

        //then
        assertEquals(mockProductCode, product.getProductCode());
        assertEquals(mockProduct.getName(), product.getName());
        assertEquals(mockProduct.getSubName(), product.getSubName());
        assertEquals(mockProduct.getDescription(), product.getDescription());
        assertEquals(mockProduct.getOptions(), product.getOptions());
    }

    @Test
    void getByProductCode_NotExistProductException() {

        // given
        given(productRepository.findByProductCode(anyString())).willReturn(Optional.empty());

        // when, then
        CustomException customException =
                assertThrows(CustomException.class, () -> productService.getByProductCode(mockProductCode));
        assertEquals(ErrorCode.NOT_EXIST_PRODUCT, customException.getErrorCode());
    }

    @Test
    void getByMemberIdAndProductCode() {

        // given
        Product mockProduct = setMockProduct(mockProductCode, mockMemberId);
        given(productRepository.findByMemberIdAndProductCode(mockMemberId, mockProductCode)).willReturn(Optional.of(mockProduct));

        // when
        Product product = productService.getByMemberIdAndProductCode(mockMemberId, mockProductCode);

        // then
        assertEquals(mockProductCode, product.getProductCode());
        assertEquals(mockMemberId, product.getMemberId());
        assertEquals(mockProduct.getName(), product.getName());
        assertEquals(mockProduct.getSubName(), product.getSubName());
        assertEquals(mockProduct.getDescription(), product.getDescription());
        assertEquals(mockProduct.getOptions(), product.getOptions());
    }

    @Test
    void getByMemberIdAndProductCode_NotExistProductException() {

        // given
        given(productRepository.findByMemberIdAndProductCode(anyLong(), anyString())).willReturn(Optional.empty());

        // when, then
        CustomException customException =
                assertThrows(CustomException.class, () -> productService.getByMemberIdAndProductCode(mockMemberId, mockProductCode));
        assertEquals(ErrorCode.NOT_EXIST_PRODUCT, customException.getErrorCode());
    }

//    @Test
//    void searchProduct() {
//
//
//    }

    private Product setMockProduct(String productCode , Long memberId) {

        return Product.builder()
                .productCode(productCode)
                .memberId(memberId)
                .categoryProduct(setMockCategoryProduct(mockCategoryCode))
                .name("MOCK_PRODUCT_NAME")
                .subName("MOCK_SUB_NAME")
                .description("MOCK_DESCRIPTION")
                .sellStatus(SellStatus.WAITING_SALE)
                .isDelete(false)
                .build();
    }

    private CategoryProduct setMockCategoryProduct(String categoryCode) {

        return CategoryProduct.builder()
                .categoryCode(categoryCode)
                .build();
    }

    private CreateProductForm setMockCreateProductForm() {

        return CreateProductForm.builder()
                .categoryCode(mockCategoryCode)
                .name("MOCK_PRODUCT_NAME")
                .subName("MOCK_SUB_NAME")
                .description("MOCK_DESCRIPTION")
                .build();
    }

    private UpdateProductForm setMockUpdateProductForm() {

        return UpdateProductForm.builder()
                .categoryCode("UPDATE_CATEGORY_CODE")
                .name("UPDATE_PRODUCT_NAME")
                .subName("UPDATE_SUB_NAME")
                .description("UPDATE_DESCRIPTION")
                .sellStatus(SellStatus.ON_SALE.getCode())
                .build();
    }
}
