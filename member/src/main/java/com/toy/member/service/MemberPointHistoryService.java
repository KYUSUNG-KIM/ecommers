package com.toy.member.service;

import com.toy.member.dto.ChangePointForm;
import com.toy.member.entity.MemberPointHistory;
import com.toy.member.repository.MemberPointHistoryRepository;
import com.toy.ecommercecommon.global.exception.CustomException;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPointHistoryService {

    private final MemberService memberService;

    private final MemberPointHistoryRepository memberPointHistoryRepository;


    @Transactional(noRollbackFor = CustomException.class)
    public MemberPointHistory saveHistory(Long memberId, final ChangePointForm form) {

        MemberPointHistory memberPointHistory =
                memberPointHistoryRepository.findFirstByMember_memberIdOrderByHistoryIdDesc(memberId)
                        .orElse(MemberPointHistory.initHistory(memberService.getById(memberId)
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))));

        final int resultMoney = memberPointHistory.getCurrentPoint() + form.getChangePoint();

        return memberPointHistoryRepository.save(
                MemberPointHistory.newHistory(form, resultMoney, memberPointHistory.getMember()));
    }

}
