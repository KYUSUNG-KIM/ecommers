package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySellerIdAndProductCode(Long sellerId, String productCode);

    @EntityGraph(attributePaths = "options", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findWithOptionsBySellerIdAndProductCode(Long sellerId, String productCode);

    @EntityGraph(attributePaths = "options", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findByProductCode(String productCode);
}
