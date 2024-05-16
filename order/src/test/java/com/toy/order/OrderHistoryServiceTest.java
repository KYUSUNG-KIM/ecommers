package com.toy.order;

import com.toy.order.client.ProductFeignClient;
import com.toy.order.client.ProductOptionFeignResponse;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.dto.OrderOptionForm;
import com.toy.order.dto.OrderRequestForm;
import com.toy.order.entity.Order;
import com.toy.order.entity.OrderDetail;
import com.toy.order.repository.OrderRepository;
import com.toy.order.service.OrderDetailService;
import com.toy.order.service.OrderHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderHistoryServiceTest {

    @InjectMocks
    private OrderHistoryService orderHistoryService;

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private OrderDetailService orderDetailService;

    @Mock
    private OrderRepository orderRepository;


    @Test
    void createOrder() {

        // given
        String optionCode1 = "MOCK_OPTION_CODE_1";
        String optionCode2 = "MOCK_OPTION_CODE_2";
        List<OrderOptionForm> optionForms
                = List.of(setOrderOptionForm(optionCode1, 10), setOrderOptionForm(optionCode2, 10));
        OrderRequestForm form = setOrderRequestForm(10000, optionForms);
        String email = "test@eccommerce.com";
        PaymentMethod paymentMethod = PaymentMethod.POINT;

        ProductOptionFeignResponse optionResponse1 = setOptionResponse(optionCode1, 1000);
        ProductOptionFeignResponse optionResponse2 = setOptionResponse(optionCode2, 2000);

        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(productFeignClient.getProductOption(optionCode1)).willReturn(optionResponse1);
        given(productFeignClient.getProductOption(optionCode2)).willReturn(optionResponse2);
        given(orderDetailService.save(any(OrderDetail.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Order order = orderHistoryService.createOrder(form, email, paymentMethod);

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

    private ProductOptionFeignResponse setOptionResponse(String optionCode, int price) {

        return ProductOptionFeignResponse.builder()
                .optionCode(optionCode)
                .price(price)
                .build();
    }
}
