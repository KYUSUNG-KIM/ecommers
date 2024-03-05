package com.toy.ecommerce.user.repository;

import com.toy.ecommerce.user.entity.CustomerRole;
import com.toy.ecommerce.user.entity.CustomerRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole, CustomerRoleId> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from customer_role where customer_id = ?1")
    public void deleteByCustomerId(long customerId);
}
