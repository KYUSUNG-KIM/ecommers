package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.product.constants.SellStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;                     // PK

    @NotNull
    @Column(length = 100)
    private String productCode;                 // 상품 코드, UK

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryProduct categoryProduct;    // 상품 카테고리 (FK)

    @NotNull
    @Column(length = 100)
    private String name;                        // 상품명

    @Column(length = 1000)
    private String subName;                     // 서브 이름

    @Column(columnDefinition = "int unsigned")
    private int price;                          // 가격

    private String description;                 // 설명

    @NotNull
    @Enumerated
    private SellStatus sellStatus;              // 판매 상태

    private boolean isDelete = false;           // 삭제 여부
}
