package com.toy.ecommerce.user.repository;

import com.toy.ecommerce.user.entity.CustomerPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPointHistoryRepository extends JpaRepository<CustomerPointHistory, Long> {
    Optional<CustomerPointHistory> findFirstByCustomer_customerIdOrderByHistoryIdDesc(Long customerId);
}
