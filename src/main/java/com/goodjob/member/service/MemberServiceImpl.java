package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 김도현 22.9.29 작성
 **/

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member register(Member member) {
        return memberRepository.save(member);
    }
    @Override
    public Long countByMemLoginId(String memLoginId) {
        Long result = memberRepository.countByMemLoginId(memLoginId);
        return result;
    }

    @Override
    public Optional<Member> loginIdCheck(String memLoginId) {
       return memberRepository.findByMemLoginId(memLoginId);

    }

}
