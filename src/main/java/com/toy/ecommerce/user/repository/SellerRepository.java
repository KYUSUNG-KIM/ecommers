package com.toy.ecommerce.user.repository;

import com.toy.ecommerce.user.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findOneByEmailAndVerifyIsTrue(String email);
}
