package com.toy.ecommerce.order.service;

import com.toy.ecommerce.order.dto.OrderForm;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.product.service.ProductOptionInventoryService;
import com.toy.ecommerce.product.service.ProductOptionService;
import com.toy.ecommerce.user.dto.ChangePointForm;
import com.toy.ecommerce.user.service.CustomerPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductOptionService productOptionService;
    private final CustomerPointService customerPointService;
    private final ProductOptionInventoryService productOptionInventoryService;


    @Transactional
    public void order(String email, OrderForm form) {

        // 1. 물건들이 전부 주문 가능한 상태인지 확인
        productOptionService.checkPurchasable(
                form.getOptions().stream()
                .map(OrderOptionForm::to)
                .collect(Collectors.toList())
        );

        // 2. 가격 변동이 있었는지 확인
        // todo

        // 3. 고객의 돈이 충분한지 확인 및 결제
        customerPointService.changePoint(email, ChangePointForm.builder().point(form.getTotalAmount()).build());

        // 4. 상품의 재고 관리
        productOptionInventoryService.deductInventory(
                form.getOptions().stream()
                .map(OrderOptionForm::to)
                .collect(Collectors.toList())
        );

        // 5. 주문 내역 생성
        // todo
    }

}
