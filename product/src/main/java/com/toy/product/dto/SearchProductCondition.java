package com.toy.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductCondition {

    @NotNull
    private String searchKeywordType;

    private String keyword;
}
