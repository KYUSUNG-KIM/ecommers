package com.toy.member.security.service;

import com.toy.member.entity.Member;
import com.toy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;


    @Override
    public User loadUserByUsername(final String email) throws UsernameNotFoundException {

        Member member = memberService.getVerifiedMemberByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "-> 유효하지 않은 사용자 정보입니다."));

        List<GrantedAuthority> grantedAuthorities = member.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }


}
