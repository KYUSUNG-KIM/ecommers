package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {
}
