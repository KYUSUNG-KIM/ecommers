package com.toy.order.entity;

import com.toy.ecommercecommon.global.entity.BaseEntity;
import com.toy.order.constants.OrderStatus;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.constants.PaymentStatus;
import com.toy.order.util.OrderCodeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
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

    @NotNull
    private LocalDate orderDate;

    @Column(nullable = false)
    private String email;

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

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    public static Order create(String email,
                               PaymentMethod paymentMethod,
                               Integer totalAmount) {

        return Order.builder()
                .orderCode(OrderCodeUtil.generateOrderCode())
                .orderDate(LocalDate.now())
                .email(email)
                .orderStatus(OrderStatus.SUCCESS)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.COMPLETED)
                .totalAmount(totalAmount)
                .build();
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
}