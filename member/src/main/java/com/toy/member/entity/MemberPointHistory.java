package com.toy.member.entity;

import com.toy.member.dto.ChangePointForm;
import com.toy.ecommercecommon.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ecc_member_point_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int changePoint;

    private int currentPoint;

    private String fromMessage;

    private String description;


    public static MemberPointHistory initHistory(Member member) {

        return MemberPointHistory.builder()
                .changePoint(0)
                .currentPoint(0)
                .member(member)
                .build();
    }

    public static MemberPointHistory newHistory(ChangePointForm form,
                                                int resultMoney,
                                                Member member) {
        return MemberPointHistory.builder()
                .changePoint(form.getChangePoint())
                .currentPoint(resultMoney)
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .member(member)
                .build();
    }
}
