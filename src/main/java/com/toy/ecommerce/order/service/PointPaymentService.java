package com.toy.ecommerce.order.service;

import com.toy.ecommerce.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointPaymentService implements PaymentInterface {

    private final CustomerService customerService;

    @Override
    public boolean payment() {


        return false;
    }

    @Override
    public boolean refund() {
        return false;
    }
}
