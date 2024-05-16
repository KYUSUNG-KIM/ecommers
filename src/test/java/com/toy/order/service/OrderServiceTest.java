package com.toy.order.service;

import com.toy.ecommerce.order.dto.OrderDto;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.entity.Order;
import com.toy.ecommerce.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
//    @Rollback(value = false)
    void order() {

        String email = "goodks09@test.com";
        String phoneNumber = "010-1234-5678";

        OrderOptionForm optionForm1 = new OrderOptionForm();
        optionForm1.setOptionCode("P240306HJI8RiEtee");
        optionForm1.setQuantity(500);

        OrderOptionForm optionForm2 = new OrderOptionForm();
        optionForm2.setOptionCode("P240306HJI8RisB3e");
        optionForm2.setQuantity(4);

        List<OrderOptionForm> options = new ArrayList<>();
        options.add(optionForm1);
        options.add(optionForm2);

        OrderRequestForm form = new OrderRequestForm();
        form.setTotalAmount(10000);
        form.setOptions(options);

        Order order = orderService.order(email, phoneNumber, form);

        System.out.println(OrderDto.from(order));
    }

}