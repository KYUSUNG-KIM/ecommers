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
    public void changePoint(String email, final ChangePointForm form) {

        Customer customer = customerService.getVerifiedCustomerByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        checkPoint(customer.getPoint(), form.getPoint());

        customer.changePoint(form.getPoint());
        customerService.save(customer);

        customerPointHistoryService.saveHistory(customer.getCustomerId(), form);
    }


    private void checkPoint(final int currentPoint, final int changeAmount) {
        if (currentPoint + changeAmount > 0) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }
    }

}
