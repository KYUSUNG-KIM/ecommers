package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.dto.CreateOptionForm;
import com.toy.ecommerce.product.dto.UpdateOptionForm;
import com.toy.ecommerce.product.util.ProductCodeUtil;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_OPTION")
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
    private int extraAmount;            // 추가 금액

    @OneToOne(mappedBy = "productOption", cascade = CascadeType.ALL)
    private ProductOptionInventory optionInventory;


    public static ProductOption create(Product product, CreateOptionForm form) {

        String optionCode = ProductCodeUtil.generateOptionCode(product.getProductCode());

        return ProductOption.builder()
                .optionCode(optionCode)
                .product(product)
                .optionName(form.getOptionName())
                .sellStatus(SellStatus.ON_SALE)
                .extraAmount(form.getExtraAmount())
                .optionInventory(ProductOptionInventory.of(optionCode, form.getInventory()))
                .build();
    }


    public static ProductOption update(ProductOption option, UpdateOptionForm form) {

        return option.toBuilder()
                .optionName(form.getOptionName())
                .sellStatus(SellStatus.valueOf(form.getSellStatus()))
                .extraAmount(form.getExtraAmount())
                .optionInventory(ProductOptionInventory.update(option.getOptionInventory(), form.getInventory()))
                .sellStatus(SellStatus.valueOf(form.getSellStatus()))
                .build();
    }

}
