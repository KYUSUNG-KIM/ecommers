package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {
}
