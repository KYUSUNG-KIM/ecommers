package com.toy.ecommerce.order.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.entity.Order;
import com.toy.ecommerce.order.entity.OrderDetail;
import com.toy.ecommerce.order.repository.OrderRepository;
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
    public Order createHistory(OrderRequestForm form,
                               String email,
                               String phoneNumber,
                               PaymentMethod paymentMethod) {

        Order newOrder = orderRepository.save(
                Order.newOrder(email, phoneNumber, paymentMethod, form.getTotalAmount()));

        for (OrderOptionForm optionForm : form.getOptions()) {
            orderDetailService.save(formToOptionDetail(newOrder, optionForm));
        }

        return newOrder;
    }

    private OrderDetail formToOptionDetail(Order order, OrderOptionForm form) {

        return productOptionService.getByOptionCode(form.getOptionCode())
                .map(option -> OrderDetail.builder()
                        .order(order)
                        .optionCode(option.getOptionCode())
                        .productName(option.getProduct() + " " + option.getOptionName())
                        .price(option.getPrice())
                        .quantity(form.getQuantity())
                        .totalAmount(option.getPrice() * form.getQuantity())
                        .build()
                )
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_OPTION));
    }

}