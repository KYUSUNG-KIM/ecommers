package com.toy.order.dto;

import com.toy.order.constants.OrderStatus;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.constants.PaymentStatus;
import com.toy.order.entity.Order;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto implements Serializable {

    private Long orderId;

    private String orderCode;

    private String email;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Integer totalAmount;

    private List<OrderDetailDto> details = new ArrayList<>();


    public static OrderDto from(Order order) {

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .email(order.getEmail())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .totalAmount(order.getTotalAmount())
                .details(order.getOrderDetails().stream().map(OrderDetailDto::from).collect(Collectors.toList()))
                .build();
    }
}
