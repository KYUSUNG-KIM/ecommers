package com.toy.ecommerce.user.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.user.dto.ChangePointForm;
import com.toy.ecommerce.user.entity.CustomerPointHistory;
import com.toy.ecommerce.user.repository.CustomerPointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerPointHistoryService {

    private final CustomerService customerService;

    private final CustomerPointHistoryRepository customerPointHistoryRepository;


    @Transactional(noRollbackFor = CustomException.class)
    public CustomerPointHistory saveHistory(Long customerId, final ChangePointForm form) {

        CustomerPointHistory customerBalanceHistory =
                customerPointHistoryRepository.findFirstByCustomer_customerIdOrderByHistoryIdDesc(customerId)
                        .orElse(CustomerPointHistory.initHistory(customerService.getById(customerId)
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))));

        final int resultMoney = customerBalanceHistory.getCurrentPoint() + form.getChangePoint();

        return customerPointHistoryRepository.save(
                CustomerPointHistory.newHistory(form, resultMoney, customerBalanceHistory.getCustomer()));
    }

}
