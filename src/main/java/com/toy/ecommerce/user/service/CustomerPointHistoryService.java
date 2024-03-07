package com.toy.ecommerce.user.service;

import com.toy.ecommerce.global.exception.CustomException;
import com.toy.ecommerce.global.exception.ErrorCode;
import com.toy.ecommerce.user.dto.ChangePointForm;
import com.toy.ecommerce.user.entity.Customer;
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
                customerPointHistoryRepository.findFirstByCustomer_idOrderByIdDesc(customerId)
                        .orElse(initHistory(customerId));

        final int resultMoney = customerBalanceHistory.getCurrentPoint() + form.getPoint();

        return customerPointHistoryRepository.save(
                setNewHistory(form, resultMoney, customerBalanceHistory.getCustomer()));
    }


    private CustomerPointHistory initHistory(Long customerId) {

        return CustomerPointHistory.builder()
                .changePoint(0)
                .currentPoint(0)
                .customer(customerService.getById(customerId)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)))
                .build();
    }

    private CustomerPointHistory setNewHistory(ChangePointForm form,
                                               int resultMoney,
                                               Customer customer) {

        return CustomerPointHistory.builder()
                .changePoint(form.getPoint())
                .currentPoint(resultMoney)
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customer)
                .build();
    }

}
