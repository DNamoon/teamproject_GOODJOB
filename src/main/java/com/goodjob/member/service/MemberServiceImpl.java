package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

/**
 * 박채원 22.10.02 작성
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResumeMemberDTO bringMemInfo(String loginId) {
        Member member = memberRepository.findLoginInfo(loginId);
        String[] phoneNum = member.getMemPhone().split("-");
        String[] email = member.getMemEmail().split("@");
        String[] address = member.getMemAddress().split("@");
        
        ResumeMemberDTO resumeMemberDTO = entityToDTO(member, phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return resumeMemberDTO;
    }

/**
 * 김도현 22.9.29 작성
 **/

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

    @Override
    public MemberDTO memInfo(String loginId) {
        Member mem = memberRepository.findLoginInfo(loginId);
        String name = mem.getMemName();
        String[] email = mem.getMemEmail().split("@");
        String phone = mem.getMemPhone();
        String gender = mem.getMemGender();
        Date birth = mem.getMemBirthDate();
        MemberDTO memberDTO = MemberDTO.builder().memName(name).memEmail1(email[0]).memEmail2(email[1]).memPhone(phone).memGender(gender)
                .memBirthDate(birth).build();

        return memberDTO;
    }

}
