package com.toy.ecommerce.user.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.user.dto.CustomerDto;
import com.toy.ecommerce.user.entity.Customer;
import com.toy.ecommerce.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    @Transactional(readOnly = true)
    public Optional<Customer> getById(Long customerId) {

        return customerRepository.findById(customerId);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getVerifiedCustomerByEmail(String email) {

        return customerRepository.findOneByEmailAndVerifyIsTrue(email);
    }

    @Transactional(readOnly = true)
    public CustomerDto getCustomerDto(String email) {

        Customer customer = customerRepository.findOneByEmailAndVerifyIsTrue(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return CustomerDto.of(customer);
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(String email) {

        return customerRepository.existsByEmail(email);
    }

    @Transactional
    public Customer save(Customer customer) {

        return customerRepository.save(customer);
    }

    @Transactional
    public void deductPoint(Long customerId, int point) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (! hasEnoughMoney(customer, point)) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }
        else {

        }
    }

    private boolean hasEnoughMoney(Customer customer, final int point) {
        final int calculatedPoint = customer.getPoint() - point;
        return calculatedPoint > 0;
    }

}
