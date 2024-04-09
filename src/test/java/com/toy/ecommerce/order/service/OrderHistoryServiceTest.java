package com.toy.ecommerce.order.service;

import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.entity.Order;
import com.toy.ecommerce.order.entity.OrderDetail;
import com.toy.ecommerce.order.repository.OrderRepository;
import com.toy.ecommerce.product.entity.Product;
import com.toy.ecommerce.product.entity.ProductOption;
import com.toy.ecommerce.product.service.ProductOptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderHistoryServiceTest {

    @InjectMocks
    private OrderHistoryService orderHistoryService;

    @Mock
    private ProductOptionService productOptionService;

    @Mock
    private OrderDetailService orderDetailService;

    @Mock
    private OrderRepository orderRepository;


    @Test
    void createOrder() {

        // given
        String optionCode1 = "MOCK_OPTION_CODE_1";
        String optionCode2 = "MOCK_OPTION_CODE_2";
        List<OrderOptionForm> optionForms = List.of(setOrderOptionForm(optionCode1, 10), setOrderOptionForm(optionCode2, 10));
        OrderRequestForm form = setOrderRequestForm(10000, optionForms);
        String email = "test@eccommerce.com";
        String phoneNumber = "010-0000-1234";
        PaymentMethod paymentMethod = PaymentMethod.POINT;

        ProductOption productOption1 = setOption(optionCode1, 1000);
        ProductOption productOption2 = setOption(optionCode2, 2000);

        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(productOptionService.getByOptionCode(optionCode1)).willReturn(Optional.of(productOption1));
        given(productOptionService.getByOptionCode(optionCode2)).willReturn(Optional.of(productOption2));
        given(orderDetailService.save(any(OrderDetail.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Order order = orderHistoryService.createOrder(form, email, phoneNumber, paymentMethod);

        // then
        verify(orderRepository).save(any(Order.class));
        verify(orderDetailService, times(form.getOptions().size())).save(any(OrderDetail.class));
        assertNotNull(order);
        assertEquals(order.getOrderDetails().size(), 2);
    }

    private OrderRequestForm setOrderRequestForm(int totalAmount, List<OrderOptionForm> options) {

        OrderRequestForm form = new OrderRequestForm();
        form.setTotalAmount(totalAmount);
        form.setOptions(options);

        return form;
    }

    private OrderOptionForm setOrderOptionForm(String optionCode, int quantity) {

        OrderOptionForm form = new OrderOptionForm();
        form.setOptionCode(optionCode);
        form.setQuantity(quantity);

        return form;
    }

    private ProductOption setOption(String optionCode, int price) {

        return ProductOption.builder()
                .optionCode(optionCode)
                .product(new Product())
                .price(price)
                .build();
    }
}