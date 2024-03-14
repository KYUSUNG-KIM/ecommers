package com.toy.ecommerce.user.entity;

import com.toy.ecommerce.user.dto.ChangePointForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ecc_customer_point_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int changePoint;

    private int currentPoint;

    private String fromMessage;

    private String description;


    public static CustomerPointHistory initHistory(Customer customer) {

        return CustomerPointHistory.builder()
                .changePoint(0)
                .currentPoint(0)
                .customer(customer)
                .build();
    }

    public static CustomerPointHistory newHistory(ChangePointForm form,
                                                  int resultMoney,
                                                  Customer customer) {
        return CustomerPointHistory.builder()
                .changePoint(form.getChangePoint())
                .currentPoint(resultMoney)
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customer)
                .build();
    }
}
