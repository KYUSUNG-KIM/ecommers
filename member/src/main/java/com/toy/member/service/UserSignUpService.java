package com.toy.member.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.member.constants.MemberRole;
import com.toy.member.dto.MemberSignUpForm;
import com.toy.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final MemberService memberService;
    private final MemberRoleService memberRoleService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void memberSignUp(MemberSignUpForm form) {

        String signUpEmail = form.getEmail();
        boolean isExistEmail = memberService.isExistEmail(signUpEmail);

        if (isExistEmail) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }

        String encodePassword = passwordEncoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(signUpEmail)
                .password(encodePassword)
                .memberName(form.getMemberName())
                .verify(false)
                .verificationCode(RandomStringUtils.random(10, true, true))
                .verifyExpiredAt(LocalDateTime.now().plusDays(1))
                .build();

        memberService.save(member);

        List<String> roles = List.of(MemberRole.CUSTOMER.getAuthorities().split(","));
        memberRoleService.updateMemberRoles(member.getMemberId(), roles);
    }


}
