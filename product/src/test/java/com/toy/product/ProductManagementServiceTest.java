package com.toy.product;

import com.toy.product.constants.SellStatus;
import com.toy.product.dto.CreateOptionForm;
import com.toy.product.dto.CreateProductForm;
import com.toy.product.dto.UpdateOptionForm;
import com.toy.product.dto.UpdateProductForm;
import com.toy.product.entity.CategoryProduct;
import com.toy.product.entity.Product;
import com.toy.product.entity.ProductOption;
import com.toy.product.entity.ProductOptionInventory;
import com.toy.product.service.ProductManagementService;
import com.toy.product.service.ProductOptionService;
import com.toy.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductManagementServiceTest {

    @InjectMocks
    private ProductManagementService productManagementService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductOptionService productOptionService;


    @Test
    void createProduct() {

        // given
        Long memberId = 1L;
        CreateProductForm form = setCreateProductForm();
        CategoryProduct mockCategoryProduct = CategoryProduct.builder()
                .categoryCode(form.getCategoryCode())
                .build();
        Product mockProduct = Product.create(memberId, mockCategoryProduct, form);
        given(productService.create(eq(memberId), any(CreateProductForm.class))).willReturn(mockProduct);
        given(productOptionService.save(any(ProductOption.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Product createdProduct = productManagementService.createProduct(memberId, form);

        // then
        assertNotNull(createdProduct);
        verify(productService).create(eq(memberId), any(CreateProductForm.class));
        verify(productOptionService, atLeastOnce()).save(any(ProductOption.class));
        assertEquals(createdProduct.getProductCode(), mockProduct.getProductCode());
        assertEquals(createdProduct.getName(), form.getName());
        assertEquals(createdProduct.getSubName(), form.getSubName());
        assertEquals(createdProduct.getDescription(), form.getDescription());
        assertEquals(createdProduct.getCategoryProduct().getCategoryCode(), form.getCategoryCode());
        form.getOptions().forEach(o -> {
            assertTrue(createdProduct.getOptions().stream()
                    .anyMatch(option -> StringUtils.equals(o.getOptionName(), option.getOptionName())));
        });
    }

    @Test
    void updateProduct_success() {

        // given
        Long memberId = 1L;
        UpdateProductForm form = setUpdateProductForm();
        Product mockProduct = setProductByUpdateProductForm(form);

        given(productService.update(anyLong(), anyString(), any(UpdateProductForm.class))).willReturn(mockProduct);
        given(productOptionService.save(any(ProductOption.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Product updateProduct = productManagementService.updateProduct(memberId, mockProduct.getProductCode(), form);
        ArgumentCaptor<ProductOption> productOptionCaptor = ArgumentCaptor.forClass(ProductOption.class);

        // then
        assertNotNull(updateProduct);
        verify(productService).update(eq(memberId), anyString(), any(UpdateProductForm.class));
        verify(productOptionService, times(form.getOptions().size())).save(any(ProductOption.class));
        assertEquals(mockProduct.getProductCode(), updateProduct.getProductCode());
        assertEquals(form.getName(), updateProduct.getName());
        assertEquals(form.getSubName(), updateProduct.getSubName());
        assertEquals(form.getDescription(), updateProduct.getDescription());
        assertEquals(form.getCategoryCode(), updateProduct.getCategoryProduct().getCategoryCode());

        verify(productOptionService, times(form.getOptions().size())).save(productOptionCaptor.capture());
        List<ProductOption> capturedOptions = productOptionCaptor.getAllValues();
        form.getOptions().forEach(o -> {
            assertTrue(capturedOptions.stream()
                    .anyMatch(option -> StringUtils.equals(option.getOptionCode(), o.getOptionCode())));

            ProductOption productOption = capturedOptions.stream()
                    .filter(option -> StringUtils.equals(option.getOptionCode(), o.getOptionCode()))
                    .findFirst()
                    .orElseThrow();

            assertEquals(o.getOptionName(), productOption.getOptionName());
            assertEquals(o.getInventory(), productOption.getOptionInventory().getInventory());
            assertEquals(o.getPrice(), productOption.getPrice());
            assertEquals(o.getSellStatus(), productOption.getSellStatus().getCode());
        });
    }

    private CreateProductForm setCreateProductForm() {

        CreateOptionForm optionForm1 = CreateOptionForm.builder()
                .optionName("OPTION_1")
                .price(1000)
                .inventory(5000)
                .build();

        CreateOptionForm optionForm2 = CreateOptionForm.builder()
                .optionName("OPTION_2")
                .price(2000)
                .inventory(1000)
                .build();

        return CreateProductForm.builder()
                .categoryCode("CATEGORY_CODE")
                .name("Test Product")
                .subName("Sub Name")
                .description("description")
                .options(List.of(optionForm1, optionForm2))
                .build();
    }

    private UpdateProductForm setUpdateProductForm() {

        UpdateOptionForm optionForm1 = UpdateOptionForm.builder()
                .optionCode("OPTION_CODE_1")
                .optionName("OPTION_1")
                .price(1000)
                .inventory(5000)
                .sellStatus(SellStatus.WAITING_SALE.getCode())
                .build();

        UpdateOptionForm optionForm2 = UpdateOptionForm.builder()
                .optionCode("OPTION_CODE_2")
                .optionName("OPTION_2")
                .price(2000)
                .inventory(1000)
                .sellStatus(SellStatus.WAITING_SALE.getCode())
                .build();

        return UpdateProductForm.builder()
                .categoryCode("CATEGORY_CODE")
                .name("Test Product")
                .subName("Sub Name")
                .description("description")
                .sellStatus(SellStatus.ON_SALE.getCode())
                .options(List.of(optionForm1, optionForm2))
                .build();
    }

    private Product setProductByUpdateProductForm(UpdateProductForm form) {

        String productCode = "PRODUCT_CODE";

        CategoryProduct categoryProduct = CategoryProduct.builder()
                .categoryCode(form.getCategoryCode())
                .build();

        Product mockProduct = Product.builder().productCode(productCode).build()
                .update(categoryProduct, form);

        List<ProductOption> mockOptions = form.getOptions()
                .stream()
                .map(o -> ProductOption.builder()
                        .optionCode(o.getOptionCode())
                        .product(mockProduct)
                        .optionInventory(new ProductOptionInventory())
                        .build())
                .collect(Collectors.toList());

        return mockProduct.toBuilder()
                .options(mockOptions)
                .build();
    }
}
