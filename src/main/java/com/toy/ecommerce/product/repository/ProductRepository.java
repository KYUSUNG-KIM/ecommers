package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.CategoryProduct;
import com.toy.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
