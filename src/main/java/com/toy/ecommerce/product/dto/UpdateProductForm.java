package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateProductForm {

    @NotNull
    private String categoryCode;        // 상품 카테고리

    @NotNull
    @Size(max = 100)
    private String name;                // 상품명

    @Size(max = 1000)
    private String subName;             // 서브 이름

    private String description;         // 설명

    @NotNull
    private String sellStatus;          // 판매 상태

    private List<UpdateOptionForm> options = new ArrayList<>();     // 상품 옵션
}
