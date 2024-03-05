package com.toy.ecommerce.user.repository;

import com.toy.ecommerce.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findOneByEmailAndVerifyIsTrue(String email);

    boolean existsByEmail(String email);
}
