package com.toy.ecommerce.user.repository;

import com.toy.ecommerce.user.entity.CustomerPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface CustomerPointHistoryRepository extends JpaRepository<CustomerPointHistory, Long> {
    Optional<CustomerPointHistory> findFirstByCustomer_idOrderByIdDesc(@RequestParam("customer_id") Long customerId);
}
