package com.toy.order.dto;

import com.toy.order.constants.OrderStatus;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.constants.PaymentStatus;
import com.toy.order.entity.Order;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDto {

    private String orderCode;

    private String email;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Integer totalAmount;


    public static OrderHistoryDto from(Order order) {

        return OrderHistoryDto.builder()
                .orderCode(order.getOrderCode())
                .email(order.getEmail())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .totalAmount(order.getTotalAmount())
                .build();
    }
}
