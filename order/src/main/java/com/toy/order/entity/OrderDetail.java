package com.toy.order.entity;

import com.toy.ecommercecommon.global.entity.BaseEntity;
import com.toy.order.dto.OrderDetailParam;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ecc_order_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String optionCode;

    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int totalAmount;


    public static OrderDetail of(Order order, OrderDetailParam param) {
        return OrderDetail.builder()
                .order(order)
                .optionCode(param.getOptionCode())
                .productName(param.getProductName())
                .price(param.getPrice())
                .quantity(param.getQuantity())
                .totalAmount(param.getPrice() * param.getQuantity())
                .build();
    }
}
