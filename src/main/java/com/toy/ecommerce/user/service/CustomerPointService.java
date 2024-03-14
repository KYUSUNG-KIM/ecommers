package com.toy.ecommerce.user.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.user.dto.ChangePointForm;
import com.toy.ecommerce.user.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerPointService {

    private final CustomerService customerService;
    private final CustomerPointHistoryService customerPointHistoryService;



    @Transactional(noRollbackFor = CustomException.class)
    public void deductPoint(String email, int deductPoint, String message) {

        Customer customer = customerService.getVerifiedCustomerByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (! isEnoughPoint(customer.getPoint(), deductPoint)) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }

        customer.deductPoint(deductPoint);
        customerService.save(customer);

        final int refactor = -1;
        customerPointHistoryService.saveHistory(
                customer.getCustomerId(), new ChangePointForm(email, message, deductPoint * refactor));
    }


    private boolean isEnoughPoint(int currentPoint, int deductAmount) {
        return currentPoint - deductAmount > 0;
    }

}
