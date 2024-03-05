package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "CATEGORY_PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;                     // PK

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryProduct categoryProduct;    // FK

    @NotNull
    @Column(length = 20, unique = true)
    private String categoryCode;                // 카테고리 코드, UK

    @Column(length = 100)
    private String categoryName;                // 카테고리명

}
