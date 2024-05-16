package com.toy.product.repository;

import com.toy.product.constants.SellStatus;
import com.toy.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {

    Optional<ProductOption> findByOptionCodeAndSellStatus(String optionCode, SellStatus sellStatus);

    Optional<ProductOption> findByOptionCode(String optionCode);

}
