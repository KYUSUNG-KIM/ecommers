package com.toy.ecommerce.user.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.user.constants.Role;
import com.toy.ecommerce.user.dto.CustomerSignUpForm;
import com.toy.ecommerce.user.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final CustomerService customerService;
    private final CustomerRoleService customerRoleService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void customerSignUp(CustomerSignUpForm form) {

        String signUpEmail = form.getEmail();
        boolean isExistEmail = customerService.isExistEmail(signUpEmail);

        if (isExistEmail) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }

        String encodePassword = passwordEncoder.encode(form.getPassword());

        Customer customer = Customer.builder()
                .email(signUpEmail)
                .password(encodePassword)
                .customerName(form.getCustomerName())
                .phoneNumber(form.getPhoneNumber())
                .verify(false)
                .verificationCode(RandomStringUtils.random(10, true, true))
                .verifyExpiredAt(LocalDateTime.now().plusDays(1))
                .build();

        customerService.save(customer);

        List<String> roles = List.of(Role.CUSTOMER.getAuthorities().split(","));
        customerRoleService.updateCustomerRoles(customer.getCustomerId(), roles);
    }


}
