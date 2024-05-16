package com.toy.product.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.ecommerce.product.dto.CategoryProductDto;
import com.toy.ecommerce.product.dto.CreateSubCategoryForm;
import com.toy.ecommerce.product.dto.CreateTopCategoryForm;
import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.repository.CategoryProductRepository;
import com.toy.ecommerce.product.service.CategoryProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryProductServiceTest {

    @Mock
    private CategoryProductRepository categoryProductRepository;

    @InjectMocks
    private CategoryProductService categoryProductService;



    // 이미 존재하는 카테고리 생성
    @Test
    void createCategory_CategoryCodeAlreadyExists() {

        // given
        String existingCode = "existingCode";
        CreateTopCategoryForm topForm = CreateTopCategoryForm.builder()
                .categoryCode(existingCode)
                .build();
        CreateSubCategoryForm subForm = CreateSubCategoryForm.builder()
                .categoryCode(existingCode)
                .build();
        given(categoryProductRepository.existsByCategoryCode(existingCode)).willReturn(true);

        // when, then
        CustomException customException_createTopCategory
                = assertThrows(CustomException.class, () -> categoryProductService.createTopCategory(topForm));
        assertEquals(ErrorCode.ALREADY_ADDED_CATEGORY, customException_createTopCategory.getErrorCode());

        CustomException customException_createChildCategory
                = assertThrows(CustomException.class, () -> categoryProductService.createChildCategory(subForm));
        assertEquals(ErrorCode.ALREADY_ADDED_CATEGORY, customException_createChildCategory.getErrorCode());
    }

    // 자식 카테고리 생성 - 존재하지 않는 부모 카테고리
    @Test
    void createChildCategory_NotExistPrentCategory() {

        // given
        String notExistPrentCode = "notExistPrentCode";
        String existingCode = "existingCode";
        CreateSubCategoryForm subForm = CreateSubCategoryForm.builder()
                .parentCategoryCode(notExistPrentCode)
                .categoryCode(existingCode)
                .build();
        given(categoryProductRepository.existsByCategoryCode(existingCode)).willReturn(false);

        // when, then
        CustomException customException
                = assertThrows(CustomException.class, () -> categoryProductService.createChildCategory(subForm));
        assertEquals(ErrorCode.NOT_EXIST_CATEGORY, customException.getErrorCode());
    }

    @Test
    void getByCategoryCode() {

        // given
        CategoryProduct categoryProduct = CategoryProduct.builder()
                .categoryCode("ExistCategoryCode")
                .build();
        given(categoryProductRepository.findByCategoryCode(categoryProduct.getCategoryCode())).willReturn(Optional.of(categoryProduct));

        // when
        Optional<CategoryProduct> optional = categoryProductService.getByCategoryCode(categoryProduct.getCategoryCode());

        // then
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getCategoryCode(), categoryProduct.getCategoryCode());
    }

    @Test
    void getAllCategories() {

        // given
        // 자식이 없는 최상위
        CategoryProduct topCategory = CategoryProduct.builder()
                .categoryId(1)
                .categoryCode("CAT1")
                .categoryName("카테고리 1")
                .build();

        // 자식이 있는 최상위
        CategoryProduct topCategoryWithChild = CategoryProduct.builder()
                .categoryId(2)
                .categoryCode("CAT2")
                .categoryName("카테고리 2")
                .build();

        // 자식 노드
        CategoryProduct childCategory = CategoryProduct.builder()
                .categoryId(3)
                .parent(topCategoryWithChild)
                .categoryCode("CHILD_CAT")
                .categoryName("자식 카테고리")
                .build();
        topCategoryWithChild.getChildren().add(childCategory);

        List<CategoryProduct> categoryProducts = List.of(topCategory, topCategoryWithChild);
        given(categoryProductRepository.findAllByParentNull()).willReturn(categoryProducts);

        // when
        List<CategoryProductDto> topCategories = categoryProductService.getAllCategories();

        // then
        assertNotNull(topCategories);
        assertEquals(2, topCategories.size());
        assertTrue(topCategories.stream().anyMatch(dto -> dto.getCategoryCode().equals(topCategory.getCategoryCode())));
        topCategories.stream()
                .filter(dto -> dto.getCategoryCode().equals(topCategoryWithChild.getCategoryCode()))
                .findFirst()
                .ifPresentOrElse(dto -> {
                    assertEquals(1, dto.getChildren().size());
                    assertEquals(childCategory.getCategoryCode(), dto.getChildren().get(0).getCategoryCode());
                }, Assertions::fail);
    }

    @Test
    void createChildCategory() {
        // given
        CreateSubCategoryForm form = CreateSubCategoryForm.builder()
                .parentCategoryCode("PARENT_CODE")
                .categoryCode("CHILD_CODE")
                .categoryName("CHILD_NAME")
                .build();

        CategoryProduct parentCategory = CategoryProduct.builder()
                .categoryId(1)
                .categoryCode(form.getParentCategoryCode())
                .build();

        // when
        given(categoryProductRepository.findByCategoryCode(form.getParentCategoryCode())).willReturn(Optional.of(parentCategory));
        ArgumentCaptor<CategoryProduct> categoryProductCaptor = ArgumentCaptor.forClass(CategoryProduct.class);
        categoryProductService.createChildCategory(form);

        // then
        verify(categoryProductRepository).save(categoryProductCaptor.capture());
        CategoryProduct capturedCategory = categoryProductCaptor.getValue();

        assertNotNull(capturedCategory);
        assertEquals(form.getCategoryCode(), capturedCategory.getCategoryCode());
        assertEquals(form.getCategoryName(), capturedCategory.getCategoryName());
        assertEquals(form.getParentCategoryCode(), capturedCategory.getParent().getCategoryCode());
    }

    @Test
    void createTopCategory() {

        // given
        CreateTopCategoryForm form = CreateTopCategoryForm.builder()
                .categoryCode("TOP_CATEGORY")
                .categoryName("최상위 카테고리")
                .build();

        // when
        given(categoryProductRepository.existsByCategoryCode(form.getCategoryCode())).willReturn(false);
        ArgumentCaptor<CategoryProduct> categoryProductCaptor = ArgumentCaptor.forClass(CategoryProduct.class);
        categoryProductService.createTopCategory(form);

        // then
        verify(categoryProductRepository).save(categoryProductCaptor.capture());
        CategoryProduct capturedCategory = categoryProductCaptor.getValue();

        assertNotNull(capturedCategory);
        assertEquals(form.getCategoryCode(), capturedCategory.getCategoryCode());
        assertEquals(form.getCategoryName(), capturedCategory.getCategoryName());
    }
}