package com.toy.product.entity;

import com.toy.ecommercecommon.global.entity.BaseEntity;
import com.toy.product.constants.SellStatus;
import com.toy.product.dto.CreateOptionForm;
import com.toy.product.dto.UpdateOptionForm;
import com.toy.product.util.ProductCodeUtil;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ecc_product_option")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductOption extends BaseEntity {

    @Id
    @Column(length = 30, nullable = false)
    private String optionCode;          // 옵션 코드, PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;            // 상품, FK

    @Column(length = 100, nullable = false)
    private String optionName;          // 옵션명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellStatus sellStatus;      // 판매 상태

    @Column(nullable = false)
    private int price;                  // 가격

    @OneToOne(mappedBy = "productOption", cascade = CascadeType.ALL)
    private ProductOptionInventory optionInventory;


    public static ProductOption create(Product product, CreateOptionForm form) {

        String optionCode = ProductCodeUtil.generateOptionCode(product.getProductCode());

        return ProductOption.builder()
                .optionCode(optionCode)
                .product(product)
                .optionName(form.getOptionName())
                .sellStatus(SellStatus.ON_SALE)
                .price(form.getPrice())
                .optionInventory(ProductOptionInventory.of(optionCode, form.getInventory()))
                .build();
    }

    public ProductOption update(UpdateOptionForm form) {

        return this.toBuilder()
                .optionName(form.getOptionName())
                .sellStatus(SellStatus.fromValue(form.getSellStatus()))
                .price(form.getPrice())
                .optionInventory(ProductOptionInventory.update(this.getOptionInventory(), form.getInventory()))
                .sellStatus(SellStatus.fromValue(form.getSellStatus()))
                .build();
    }
}
