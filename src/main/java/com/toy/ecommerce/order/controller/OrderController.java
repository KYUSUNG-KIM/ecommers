package com.toy.ecommerce.order.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.order.dto.OrderDto;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.service.OrderService;
import com.toy.ecommerce.user.dto.CustomerDto;
import com.toy.ecommerce.user.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping(value = "/orders")
    public CommonResponse<OrderDto> requestOrder(@Valid @RequestBody OrderRequestForm form,
                                                 Principal principal) {

        CustomerDto customerDto = customerService.getCustomerDto(principal.getName());

        return new CommonResponse<>(OrderDto.from(orderService.order(
                customerDto.getEmail(), customerDto.getPhoneNumber(), form)));
    }

}
