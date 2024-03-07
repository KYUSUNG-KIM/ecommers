package com.toy.ecommerce.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderForm {

    private int totalAmount;
    private List<OrderOptionForm> options = new ArrayList<>();
}
