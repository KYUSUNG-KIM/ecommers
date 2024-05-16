package com.toy.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/product-service/**")
                        .filters(f -> f.addRequestHeader("product-header", "product-request-header")
                                .addResponseHeader("product-header", "product-response-header"))
                        .uri("lb://PRODUCT"))
//                        .uri("http://localhost:8001"))
                .build();
    }
}
