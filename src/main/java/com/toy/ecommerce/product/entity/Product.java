package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.dto.CreateProductForm;
import com.toy.ecommerce.product.dto.UpdateProductForm;
import com.toy.ecommerce.product.util.ProductCodeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ecc_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;                     // PK

    @Column(length = 100, nullable = false)
    private String productCode;                 // 상품 코드, UK

    private long sellerId;                      // 판매자 ID, FK

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryProduct categoryProduct;    // 상품 카테고리, FK

    @Column(length = 100, nullable = false)
    private String name;                        // 상품명

    @Column(length = 1000)
    private String subName;                     // 서브 이름

    private String description;                 // 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellStatus sellStatus;              // 판매 상태

    @Column(nullable = false)
    private boolean isDelete = false;           // 삭제 여부

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductOption> options = new ArrayList<>();        // 상품 옵션


    public static Product create(Long sellerId,
                                 CategoryProduct categoryProduct,
                                 CreateProductForm form) {

        return Product.builder()
                .productCode(ProductCodeUtil.generateProductCode())
                .sellerId(sellerId)
                .categoryProduct(categoryProduct)
                .name(form.getName())
                .subName(form.getSubName())
                .description(form.getDescription())
                .sellStatus(SellStatus.WAITING_SALE)
                .isDelete(false)
                .build();
    }


    public static Product update(Product product,
                                 CategoryProduct categoryProduct,
                                 UpdateProductForm form) {

        return product.toBuilder()
                .categoryProduct(categoryProduct)
                .name(form.getName())
                .subName(form.getSubName())
                .description(form.getDescription())
                .sellStatus(SellStatus.valueOf(form.getSellStatus()))
                .build();
    }

    public void addOption(ProductOption option) {
        this.options.add(option);
        option.setProduct(this);
    }
}
