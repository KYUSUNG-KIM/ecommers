package com.toy.member.repository;

import com.toy.member.entity.MemberPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPointHistoryRepository extends JpaRepository<MemberPointHistory, Long> {
    Optional<MemberPointHistory> findFirstByMember_memberIdOrderByHistoryIdDesc(Long memberId);
}
