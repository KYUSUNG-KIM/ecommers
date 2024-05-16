package com.toy.member.repository;

import com.toy.member.entity.MemberRole;
import com.toy.member.entity.MemberRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRoleId> {

    public void deleteByMemberId(long memberId);
}
