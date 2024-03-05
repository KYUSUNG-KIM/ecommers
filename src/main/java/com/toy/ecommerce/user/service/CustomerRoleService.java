package com.toy.ecommerce.user.service;

import com.toy.ecommerce.user.entity.CustomerRole;
import com.toy.ecommerce.user.repository.CustomerRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerRoleService {

    private final CustomerRoleRepository customerRoleRepository;


    @Transactional
    public void updateCustomerRoles(long customerId, final List<String> roles) {

        customerRoleRepository.deleteByCustomerId(customerId);

        for (String role : roles) {
            CustomerRole customerRole = new CustomerRole(customerId, role);

            customerRoleRepository.save(customerRole);
        }
    }

}
