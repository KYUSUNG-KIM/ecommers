package com.toy.ecommerce.product.repository;

import com.toy.ecommerce.product.constants.SellStatus;
import com.toy.ecommerce.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {

    Optional<ProductOption> findByIdAndSellStatus(String optionCode, SellStatus sellStatus);

}
