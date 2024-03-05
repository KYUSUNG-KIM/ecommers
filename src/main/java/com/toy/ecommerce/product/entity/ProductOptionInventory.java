package com.toy.ecommerce.product.entity;


import com.toy.ecommerce.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PRODUCT_OPTION_INVENTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionInventory extends BaseEntity {

    @Id
    private String optionCode;      // 상품 옵션 코드, FK

    @Column(columnDefinition = "int(10) unsigned")
    private int inventory;          // 재고
}
