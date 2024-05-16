package com.toy.order.config;

import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class FeignClientConfig {

    /**
     * Request Header에 Authorization 값이 있다면 셋팅해준다.
     *
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes
                    = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (Objects.nonNull(requestAttributes)) {
                String accessToken = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

                if (StringUtils.isNotEmpty(accessToken)) {
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, accessToken);
                }
            }
        };
    }
}
