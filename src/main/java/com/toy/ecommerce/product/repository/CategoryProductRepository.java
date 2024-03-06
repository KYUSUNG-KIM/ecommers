package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.entity.CategoryProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {

    @EntityGraph(attributePaths = {"children"}, type = EntityGraph.EntityGraphType.LOAD)
    List<CategoryProduct> findAllByParentNull();

    Optional<CategoryProduct> findByCategoryCode(String categoryCode);

    boolean existsByCategoryCode(String categoryCode);
}
