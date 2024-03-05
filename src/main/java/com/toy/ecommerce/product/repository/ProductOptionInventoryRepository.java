package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.ProductOption;
import com.toy.ecommerce.product.entity.ProductOptionInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionInventoryRepository extends JpaRepository<ProductOptionInventory, String> {
}
