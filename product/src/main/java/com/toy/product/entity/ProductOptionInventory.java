package com.toy.product.entity;


import com.toy.ecommercecommon.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ecc_product_option_inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductOptionInventory extends BaseEntity {

    @Id
    @Column(name = "option_code", nullable = false)
    private String optionCode;      // 상품 옵션 코드, FK

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_code")
    private ProductOption productOption;        // 상품 옵션 코드, FK

    @Column(columnDefinition = "int(10) unsigned")
    private int inventory;          // 재고


    public static ProductOptionInventory of(String optionCode, int inventory) {

        return ProductOptionInventory.builder()
                .optionCode(optionCode)
                .inventory(inventory)
                .build();
    }

    public static ProductOptionInventory update(ProductOptionInventory optionInventory, int inventory) {

        return optionInventory.toBuilder()
                .inventory(inventory)
                .build();
    }

    public void deductInventory(int quantity) {
        this.setInventory(this.inventory - quantity);
    }
}
