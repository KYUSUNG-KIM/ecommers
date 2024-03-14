package com.toy.ecommerce.order.service;

import com.toy.ecommerce.order.entity.OrderDetail;
import com.toy.ecommerce.order.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;


    @Transactional
    public OrderDetail save(OrderDetail orderDetail) {

        return orderDetailRepository.save(orderDetail);
    }
}
