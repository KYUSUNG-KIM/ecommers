package com.toy.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleId implements Serializable {

    private static final long serialVersionUID = -360312474542639967L;

    private long memberId;

    private String authority;

}
