package com.toy.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchProductCondition {

    @NotNull
    private String searchKeywordType;

    private String keyword;
}
