package com.toy.member.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.member.dto.MemberSignUpForm;
import com.toy.member.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final UserSignUpService userSignUpService;


    @PostMapping(value = "/sign-up")
    public CommonResponse<String> memberSignUp(@RequestBody MemberSignUpForm dto) {

        userSignUpService.memberSignUp(dto);

        return new CommonResponse<String>("회원가입에 성공하셨습니다.");
    }
}











