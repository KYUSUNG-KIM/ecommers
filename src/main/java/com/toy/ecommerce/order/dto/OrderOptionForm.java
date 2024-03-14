package com.toy.ecommerce.order.dto;

import com.toy.ecommerce.product.dto.CheckOptionForm;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOptionForm {

    @NotNull
    private String optionCode;

    @Min(1)
    private int quantity;

    public CheckOptionForm to() {
        return new CheckOptionForm(this.optionCode, this.quantity);
    }
}
