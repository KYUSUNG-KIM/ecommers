package com.toy.ecommerce.user.controller;

import com.toy.ecommerce.global.dto.CommonResponse;
import com.toy.ecommerce.user.dto.CustomerSignUpForm;
import com.toy.ecommerce.user.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final UserSignUpService userSignUpService;


    @PostMapping(value = "/sign-up/customer")
    public CommonResponse customerSignUp(@RequestBody CustomerSignUpForm dto) {

        userSignUpService.customerSignUp(dto);

        return new CommonResponse("회원가입에 성공하셨습니다.");
    }
}











