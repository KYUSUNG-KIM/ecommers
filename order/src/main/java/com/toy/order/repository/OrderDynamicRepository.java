package com.toy.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.toy.order.entity.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderDynamicRepository {

    private final JPAQueryFactory queryFactory;


    public Page<Order> getOrders(String email, LocalDate startDate, LocalDate endDate, Pageable pageable) {

        JPAQuery<Order> orderSelectQuery = orderSelect(email, startDate, endDate);

        List<Order> orders = orderSelectQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Optional<Long> orderCount = Optional.ofNullable(orderSelectQuery
                .select(order.count())
                .fetchOne());

        return new PageImpl<>(orders, pageable, orderCount.orElse(0L));
    }

    private JPAQuery<Order> orderSelect(String email, LocalDate startDate, LocalDate endDate) {

        return queryFactory.selectFrom(order)
                .where(order.email.eq(email),
                        betweenStartDateAndEndDate(startDate, endDate)
                )
                .orderBy(order.orderId.desc());
    }

    private BooleanExpression betweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {

        int leastMonth = 3;

        if (ObjectUtils.isEmpty(startDate) && ObjectUtils.isEmpty(endDate)) {
            startDate = LocalDate.now().minusMonths(leastMonth);
            endDate = LocalDate.now();
        }
        else if (ObjectUtils.isEmpty(startDate)) {
            startDate = LocalDate.now().minusMonths(leastMonth);
        } else if (ObjectUtils.isEmpty(endDate)) {
            endDate = startDate.plusMonths(leastMonth);
        }

        return order.orderDate.between(startDate, endDate);
    }

}
