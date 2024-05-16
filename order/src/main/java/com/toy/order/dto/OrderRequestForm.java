package com.toy.order.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequestForm {

    @Min(0)
    private int totalAmount;

    private List<OrderOptionForm> options = new ArrayList<>();
}
