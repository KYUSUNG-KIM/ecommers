package com.toy.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductForm {

    @NotNull
    private String categoryCode;        // 상품 카테고리

    @NotNull
    @Size(max = 100)
    private String name;                // 상품명

    @Size(max = 1000)
    private String subName;             // 서브 이름

    private String description;         // 설명

    private List<CreateOptionForm> options = new ArrayList<>();     // 상품 옵션
}
