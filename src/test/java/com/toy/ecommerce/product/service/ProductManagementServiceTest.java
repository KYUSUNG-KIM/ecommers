package com.toy.ecommerce.product.service;

import com.toy.ecommerce.product.dto.CreateOptionForm;
import com.toy.ecommerce.product.dto.CreateProductForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class ProductManagementServiceTest {


    @Autowired
    private CategoryProductService categoryProductService;

    @Autowired
    private ProductManagementService productManagementService;



    @Test
    @Rollback(value = false)
    void createProduct() {

        String categoryCode = "TEST_1";
//        String categoryName = "테스트_코드";
//        categoryProductService.create(false, null, categoryCode, categoryName);

        CreateProductForm form = new CreateProductForm();
        form.setCategoryCode(categoryCode);
        form.setName("무지 맨투맨");
        form.setSubName("어깨가 넓어보이는 효과");
        form.setDescription("어깨가 넓어보이는 맨투맨 어좁이들한테 제격!");


        CreateOptionForm optionForm1 = new CreateOptionForm();
        optionForm1.setOptionName("블랙-L");
        optionForm1.setExtraAmount(2000);
        optionForm1.setInventory(999);

        CreateOptionForm optionForm2 = new CreateOptionForm();
        optionForm2.setOptionName("블랙-XL");
        optionForm2.setExtraAmount(2000);
        optionForm2.setInventory(999);

        form.getOptions().add(optionForm1);
        form.getOptions().add(optionForm2);

        productManagementService.createProduct(1L, form);
    }

}