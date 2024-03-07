package com.toy.ecommerce.order.dto;

import com.toy.ecommerce.product.dto.CheckOptionForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOptionForm {
    private String optionCode;
    private int quantity;

    public CheckOptionForm to() {
        return new CheckOptionForm(this.optionCode, this.quantity);
    }
}
