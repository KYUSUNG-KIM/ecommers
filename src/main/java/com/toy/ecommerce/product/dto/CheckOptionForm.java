package com.toy.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CheckOptionForm {

    private String optionCode;
    private int quantity;

}
