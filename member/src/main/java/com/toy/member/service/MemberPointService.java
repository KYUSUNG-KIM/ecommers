package com.toy.member.service;

import com.toy.member.dto.ChangePointForm;
import com.toy.member.entity.Member;
import com.toy.member.entity.MemberPointHistory;
import com.toy.ecommercecommon.global.exception.ErrorCode;
import com.toy.ecommercecommon.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberPointService {

    private final MemberService memberService;
    private final MemberPointHistoryService memberPointHistoryService;



    @Transactional(noRollbackFor = CustomException.class)
    public Integer deductPoint(String email, int deductPoint, String message) {

        Member member = memberService.getVerifiedMemberByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (! isEnoughPoint(member.getPoint(), deductPoint)) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT);
        }

        member.deductPoint(deductPoint);
        memberService.save(member);

        final int refactor = -1;
        MemberPointHistory pointHistory = memberPointHistoryService.saveHistory(
                member.getMemberId(), new ChangePointForm(email, message, deductPoint * refactor));

        return pointHistory.getCurrentPoint();
    }


    private boolean isEnoughPoint(int currentPoint, int deductAmount) {
        return currentPoint - deductAmount > 0;
    }

}
