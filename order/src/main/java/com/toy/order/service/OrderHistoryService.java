package com.toy.order.service;

import com.toy.order.client.ProductFeignClient;
import com.toy.order.client.ProductOptionFeignResponse;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.dto.OrderDetailParam;
import com.toy.order.dto.OrderOptionForm;
import com.toy.order.dto.OrderRequestForm;
import com.toy.order.entity.Order;
import com.toy.order.entity.OrderDetail;
import com.toy.order.repository.OrderDynamicRepository;
import com.toy.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final ProductFeignClient productFeignClient;
    private final OrderDetailService orderDetailService;

    private final OrderDynamicRepository orderDynamicRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequestForm form, String email, PaymentMethod paymentMethod) {

        Order newOrder = orderRepository.save(Order.create(email, paymentMethod, form.getTotalAmount()));

        for (OrderOptionForm optionForm : form.getOptions()) {
            newOrder.addOrderDetail(createOrderDetail(newOrder, optionForm));
        }

        return newOrder;
    }

    // TODO 리팩토링 필요 - 옵션 정보 일괄 조회로 개선
    private OrderDetail createOrderDetail(Order order, OrderOptionForm form) {

        ProductOptionFeignResponse option = productFeignClient.getProductOption(form.getOptionCode());

        return orderDetailService.save(OrderDetail.of(order, OrderDetailParam.of(option, form.getQuantity())));
    }

    @Transactional(readOnly = true)
    public Page<Order> getOrders(String email, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        return orderDynamicRepository.getOrders(email, startDate, endDate, pageable);
    }
}