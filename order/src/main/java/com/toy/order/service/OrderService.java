package com.toy.order.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.order.constants.PaymentMethod;
import com.toy.order.dto.CheckOptionForm;
import com.toy.order.dto.OrderOptionForm;
import com.toy.order.dto.OrderRequestForm;
import com.toy.order.entity.Order;
import com.toy.order.client.CustomerFeignClient;
import com.toy.order.client.PointDeductRequest;
import com.toy.order.client.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductFeignClient productFeignClient;
    private final CustomerFeignClient customerFeignClient;
    private final OrderHistoryService orderHistoryService;


    @Transactional
    public Order order(String email, OrderRequestForm form) {

        List<CheckOptionForm> forms = form.getOptions().stream()
                .map(OrderOptionForm::to)
                .collect(Collectors.toList());

        // 1. 물건들이 전부 주문 가능한 상태인지 확인
        boolean isEnoughInventory = productFeignClient.checkPurchasableOfProductOption(forms);
        if (! isEnoughInventory) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_INVENTORY);
        }

        // 2. 가격 변동이 있었는지 확인
        // todo 보류
        // 방안1. 상품 구매 금액 입력 받음 -> 변경됐는지 확인 ??
        // 방안2. 주문 요청 -> 상품 금액이 변동되지 않도록 고정 ??

        // 3. 고객의 돈이 충분한지 확인 및 결제
        customerFeignClient.deductPoint(email, new PointDeductRequest(form.getTotalAmount(), "상품 주문"));

        // 4. 상품의 재고 관리
        productFeignClient.deductInventory(forms);

        // 5. 주문 내역 생성
        return orderHistoryService.createOrder(form, email, PaymentMethod.POINT);
    }

}
