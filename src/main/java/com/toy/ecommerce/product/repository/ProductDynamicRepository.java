package com.toy.ecommerce.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.ecommerce.product.constants.SearchKeywordType;
import com.toy.ecommerce.product.dto.SearchProductCondition;
import com.toy.ecommerce.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.toy.ecommerce.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductDynamicRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public Page<Product> searchProducts(SearchProductCondition condition, Pageable pageable) {

        JPAQuery<Product> selectQuery = fetchSelect(condition)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        JPAQuery<Long> selectCount = fetchSelect(condition)
                .select(product.productCode.count().coalesce(0L));

        return PageableExecutionUtils.getPage(selectQuery.fetch(), pageable, selectCount::fetchOne);
    }

    private JPAQuery<Product> fetchSelect(SearchProductCondition condition) {

        return jpaQueryFactory
                .selectFrom(product)
                .where(containKeyword(condition.getSearchKeywordType(), condition.getKeyword()));
    }

    private BooleanExpression containKeyword(String searchKeywordType, String keyword) {
        SearchKeywordType keywordType = SearchKeywordType.fromValue(searchKeywordType);

        if (ObjectUtils.isNotEmpty(keywordType) && StringUtils.isNotEmpty(keyword)) {
            return switch (keywordType) {
                case PRODUCT_CODE -> product.productCode.contains(keyword);
                case PRODUCT_NAME -> product.name.contains(keyword);
            };
        }

        return null;
    }

}
