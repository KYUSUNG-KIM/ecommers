package com.toy.order.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.order.dto.OrderDto;
import com.toy.order.dto.OrderHistoryDto;
import com.toy.order.dto.OrderRequestForm;
import com.toy.order.service.OrderHistoryService;
import com.toy.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;


    @PostMapping(value = "/orders/user/{email}")
    public CommonResponse<OrderDto> requestOrder(@PathVariable("email") String email,
                                                 @Valid @RequestBody OrderRequestForm form) {

        return new CommonResponse<>(OrderDto.from(orderService.order(email, form)));
    }

    @GetMapping(value = "/orders/user/{email}")
    public CommonResponse<Page<OrderHistoryDto>> getOrderHistories(@PathVariable("email") String email,
                                                                   @RequestParam(value = "startDate", required = false)
                                                                   @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate startDate,
                                                                   @RequestParam(value = "endDate", required = false)
                                                                   @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate endDate,
                                                                   Pageable pageable) {

        return new CommonResponse<>(
                orderHistoryService.getOrders(email, startDate, endDate, pageable).map(OrderHistoryDto::from));
    }

}