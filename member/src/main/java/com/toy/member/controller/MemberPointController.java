package com.toy.member.controller;

import com.toy.member.dto.PointDeductForm;
import com.toy.member.service.MemberPointService;
import com.toy.ecommercecommon.global.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberPointController {

    private final MemberPointService memberPointService;

    @PutMapping(value = "/members/{email}/point-deduct")
    public CommonResponse<Integer> memberPointDeduct(@PathVariable("email") String email,
                                                       @Valid @RequestBody PointDeductForm form) {

        return new CommonResponse<>(memberPointService.deductPoint(email, form.getTotalAmount(), form.getMessage()));
    }
}
