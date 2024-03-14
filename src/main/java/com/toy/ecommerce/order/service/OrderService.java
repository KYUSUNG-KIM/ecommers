package com.toy.ecommerce.order.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.order.constants.PaymentMethod;
import com.toy.ecommerce.order.dto.OrderOptionForm;
import com.toy.ecommerce.order.dto.OrderRequestForm;
import com.toy.ecommerce.order.entity.Order;
import com.toy.ecommerce.product.dto.CheckOptionForm;
import com.toy.ecommerce.product.service.ProductOptionInventoryService;
import com.toy.ecommerce.product.service.ProductOptionService;
import com.toy.ecommerce.user.service.CustomerPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductOptionService productOptionService;
    private final CustomerPointService customerPointService;
    private final ProductOptionInventoryService productOptionInventoryService;
    private final OrderHistoryService orderHistoryService;


    @Transactional
    public Order order(String email, String phoneNumber, OrderRequestForm form) {

        List<CheckOptionForm> forms = form.getOptions().stream()
                .map(OrderOptionForm::to)
                .collect(Collectors.toList());

        // 1. 물건들이 전부 주문 가능한 상태인지 확인
        boolean isEnoughInventory = productOptionService.checkPurchasable(forms);
        if (! isEnoughInventory) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_INVENTORY);
        }

        // 2. 가격 변동이 있었는지 확인
        // todo 보류
        // 방안1. 상품 구매 금액 입력 받음 -> 변경됐는지 확인 ??
        // 방안2. 주문 요청 -> 상품 금액이 변동되지 않도록 고정 ??

        // 3. 고객의 돈이 충분한지 확인 및 결제
        customerPointService.deductPoint(email, form.getTotalAmount(), "상품 주문");

        // 4. 상품의 재고 관리
        productOptionInventoryService.deductInventory(forms);

        // 5. 주문 내역 생성
        return orderHistoryService.createHistory(form, email, phoneNumber, PaymentMethod.POINT);
    }

}
