package com.toy.ecommerce.order.dto;

import com.toy.ecommerce.order.constants.OrderStatus;
import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.constants.PaymentStatus;
import com.toy.ecommerce.order.entity.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;

    private String orderCode;

    private String email;

    private String phoneNumber;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Integer totalAmount;

    private Boolean isUserOrder;

    private List<OrderDetailDto> details = new ArrayList<>();


    public static OrderDto from(Order order) {

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .totalAmount(order.getTotalAmount())
                .isUserOrder(order.isUserOrder())
                .details(order.getOrderDetails().stream().map(OrderDetailDto::from).collect(Collectors.toList()))
                .build();
    }
}
