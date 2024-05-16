package com.toy.order.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.ecommercecommon.global.entity.EnumProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus  implements EnumProperty {

    ORDERED("주문 접수"),
    SUCCESS("주문 완료"),
    WAIT("주문 대기"),
    FAIL("주문 실패"),
//    DELIVERED("배송 완료"),
//    CANCELED("취소 완료"),
//    REFUND("환불 완료"),
//    PAYMENT_WAIT("결제 대기"),
//    PAYMENT_INCOMPLETE("결제 미완료")
    ;

    private String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
