package com.toy.ecommerce.security.service;

import com.toy.ecommerce.user.entity.Customer;
import com.toy.ecommerce.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;


    @Override
    public User loadUserByUsername(final String email) throws UsernameNotFoundException {

        Customer customer = customerService.getVerifiedCustomerByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "-> 유효하지 않은 사용자 정보입니다."));

        List<GrantedAuthority> grantedAuthorities = customer.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

        return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
    }


}
