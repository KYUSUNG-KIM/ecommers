package com.toy.apigateway.config;

import com.toy.apigateway.filter.AuthorizationHeaderFilter;
import jakarta.ws.rs.HttpMethod;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

//@Configuration
public class GatewayConfig {

    private final String cookieStr = "Cookie";

//    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           AuthorizationHeaderFilter authorizationHeaderFilter) {
        return builder.routes()
                .route("member-service-login", r -> r.path("/member-service/login")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.removeRequestHeader(cookieStr)
                                .rewritePath("/member-service/(?<segment>.*)", "/${segment}"))
                        .uri("lb://MEMBER-SERVICE"))
                .route("member-service-signup", r -> r.path("/member-service/sign-up")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.removeRequestHeader(cookieStr)
                                .rewritePath("/member-service/(?<segment>.*)", "/${segment}"))
                        .uri("lb://MEMBER-SERVICE"))
                .route("member-service", r -> r.path("/member-service/**")
                        .filters(f -> f.removeRequestHeader(cookieStr)
                                .rewritePath("/member-service/(?<segment>.*)", "/${segment}")
                                .filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://MEMBER-SERVICE"))
                .route("product-service", r -> r.path("/product-service/**")
                        .filters(f -> f.removeRequestHeader(cookieStr)
                                .rewritePath("/product-service/(?<segment>.*)", "/${segment}")
                                .filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order-service", r -> r.path("/order-service/**")
                        .filters(f -> f.removeRequestHeader(cookieStr)
                                .rewritePath("/order-service/(?<segment>.*)", "/${segment}")
                                .filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }

}
