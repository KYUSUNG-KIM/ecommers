package com.toy.member.entity;

import com.toy.ecommercecommon.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ecc_member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long memberId;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String memberName;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private com.toy.member.constants.MemberRole memberRole;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int point;

    @NotNull
    private String verificationCode;

    @NotNull
    private boolean verify = false;

    @NotNull
    private LocalDateTime verifyExpiredAt;

    @NotNull
    private boolean isDelete = false;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId", updatable = false)
    private List<MemberRole> roles;


    public void deductPoint(final int changeAmount) {
        this.setPoint(this.point - changeAmount);
    }
}
