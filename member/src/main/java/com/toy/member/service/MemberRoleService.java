package com.toy.member.service;

import com.toy.member.entity.MemberRole;
import com.toy.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;


    @Transactional
    public void updateMemberRoles(long memberId, final List<String> roles) {

        memberRoleRepository.deleteByMemberId(memberId);

        for (String role : roles) {
            MemberRole memberRole = new MemberRole(memberId, role);

            memberRoleRepository.save(memberRole);
        }
    }

}
