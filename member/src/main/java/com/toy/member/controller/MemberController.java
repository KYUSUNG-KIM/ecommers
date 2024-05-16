package com.toy.member.controller;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.member.dto.MemberDto;
import com.toy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping(value = "/members/{email}")
    public CommonResponse<MemberDto> getMemberInfo(@PathVariable("email") String email) {

        return new CommonResponse<>(memberService.getMemberDto(email));
    }

}