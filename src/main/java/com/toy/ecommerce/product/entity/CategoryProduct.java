package com.toy.ecommerce.product.entity;

import com.toy.ecommerce.global.entity.BaseEntity;
import com.toy.ecommerce.product.dto.CreateCategoryForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "parent_id", nullable = true)
    private CategoryProduct parent;             // 부모 카테고리, FK (최상위 카테고리 -> null)

    @NotNull
    @Column(length = 20, unique = true)
    private String categoryCode;                // 카테고리 코드, UK

    @Column(length = 100)
    private String categoryName;                // 카테고리명


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<CategoryProduct> children = new ArrayList<>();     // 자식 카테고리


    public static CategoryProduct of(CategoryProduct parent,
                                     CreateCategoryForm form) {

        return CategoryProduct.builder()
                .parent(parent)
                .categoryCode(form.getCategoryCode())
                .categoryName(form.getCategoryName())
                .build();
    }
}
