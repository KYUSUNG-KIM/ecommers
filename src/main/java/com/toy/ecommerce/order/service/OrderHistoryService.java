package com.toy.ecommerce.order.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.entity.Order;
import com.toy.ecommerce.order.entity.OrderDetail;
import com.toy.ecommerce.order.repository.OrderRepository;
import com.toy.ecommerce.product.entity.ProductOption;
import com.toy.ecommerce.product.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final ProductOptionService productOptionService;
    private final OrderDetailService orderDetailService;

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequestForm form, String email, String phoneNumber, PaymentMethod paymentMethod) {
        Order newOrder = orderRepository.save(Order.create(email, phoneNumber, paymentMethod, form.getTotalAmount()));
        form.getOptions().forEach(optionForm -> newOrder.addOrderDetail(createOrderDetail(newOrder, optionForm)));
        return newOrder;
    }

    private OrderDetail createOrderDetail(Order order, OrderOptionForm form) {
        ProductOption option = productOptionService.getByOptionCode(form.getOptionCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));

        return orderDetailService.save(newOrderDetail(order, option, form.getQuantity()));
    }

    private OrderDetail newOrderDetail(Order order, ProductOption option, int quantity) {
        return OrderDetail.builder()
                .order(order)
                .optionCode(option.getOptionCode())
                .productName(option.productAndOptionName())
                .price(option.getPrice())
                .quantity(quantity)
                .totalAmount(option.getPrice() * quantity)
                .build();
    }

}