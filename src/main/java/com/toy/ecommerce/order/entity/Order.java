package com.toy.ecommerce.order.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.order.constants.OrderStatus;
import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.constants.PaymentStatus;
import com.toy.ecommerce.order.util.OrderCodeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ecc_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(nullable = false, unique = true)
    private String orderCode;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private boolean isUserOrder;

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    public static Order newOrder(String email,
                                 String phoneNumber,
                                 PaymentMethod paymentMethod,
                                 Integer totalAmount) {

        return Order.builder()
                .orderCode(OrderCodeUtil.generateOrderCode())
                .email(email)
                .phoneNumber(phoneNumber)
                .orderStatus(OrderStatus.SUCCESS)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.COMPLETED)
                .totalAmount(totalAmount)
                .isUserOrder(false)
                .build();
    }

}