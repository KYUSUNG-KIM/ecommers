package com.toy.order.service;

import com.toy.order.entity.OrderDetail;
import com.toy.order.repository.OrderDetailRepository;
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
