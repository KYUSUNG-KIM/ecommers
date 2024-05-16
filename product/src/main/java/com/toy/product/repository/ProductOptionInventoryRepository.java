package com.toy.product.repository;

import com.toy.product.entity.ProductOptionInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionInventoryRepository extends JpaRepository<ProductOptionInventory, String> {
}
