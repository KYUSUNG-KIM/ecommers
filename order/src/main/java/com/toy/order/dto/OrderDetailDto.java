package com.toy.order.dto;

import com.toy.order.entity.OrderDetail;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {

    private Long detailId;

    private String optionCode;

    private String productName;

    private Integer quantity;

    private Integer price;

    private Integer totalAmount;


    public static OrderDetailDto from(OrderDetail orderDetail) {

        return OrderDetailDto.builder()
                .detailId(orderDetail.getDetailId())
                .optionCode(orderDetail.getOptionCode())
                .productName(orderDetail.getProductName())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .totalAmount(orderDetail.getTotalAmount())
                .build();
    }
}
