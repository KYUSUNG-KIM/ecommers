package com.toy.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product-service")
@Slf4j
public class TestController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcomeTest() {
        return "Welcome Product Service";
    }

    @GetMapping("/message")
    public String filterTest(@RequestHeader("product-header") String requestHeader) {
        log.info("header : {}", requestHeader);
        return requestHeader;
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request);
        return String.format("Hi, Product Service on Port: %s", env.getProperty("local.server.port"));
    }

}
