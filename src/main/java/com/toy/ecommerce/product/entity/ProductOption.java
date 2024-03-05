package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.product.constants.SellStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "PRODUCT_OPTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOption extends BaseEntity {

    @Id
    @Column(length = 30, nullable = false)
    private String optionCode;          // 옵션 코드, PK

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;            // 상품, FK

    @NotNull
    @Column(length = 100)
    private String optionName;          // 옵션명

    @NotNull
    @Enumerated
    private SellStatus sellStatus;      // 판매 상태

    private int extraAmount;            // 추가 금액
}
