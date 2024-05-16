package com.toy.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toy.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String email;

    @JsonProperty("name")
    private String memberName;

    private String phoneNumber;

    private int point;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .build();
    }
}
