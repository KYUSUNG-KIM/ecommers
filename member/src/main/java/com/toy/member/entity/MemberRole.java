package com.toy.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ecc_member_authority")
@Getter
@Setter
@IdClass(value = MemberRoleId.class)
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {

    @Id
    private long memberId;

    @Id
    private String authority;

}
