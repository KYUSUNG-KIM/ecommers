package com.toy.member.service;

import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.member.dto.MemberDto;
import com.toy.member.entity.Member;
import com.toy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public Optional<Member> getById(Long memberId) {

        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getVerifiedMemberByEmail(String email) {

        return memberRepository.findOneByEmailAndVerifyIsTrue(email);
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberDto(String email) {

        Member member = memberRepository.findOneByEmailAndVerifyIsTrue(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return MemberDto.of(member);
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(String email) {

        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public Member save(Member member) {

        return memberRepository.save(member);
    }

    @Transactional
    public void deductPoint(Long memberId, int point) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (! hasEnoughMoney(member, point)) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }
        else {

        }
    }

    private boolean hasEnoughMoney(Member member, final int point) {
        final int calculatedPoint = member.getPoint() - point;
        return calculatedPoint > 0;
    }

}
