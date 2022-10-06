package com.goodjob.member;

import com.goodjob.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.02 작성
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDTO bringMemInfo(String loginId) {
        Member member = memberRepository.findLoginInfo(loginId);
        String[] phoneNum = member.getMemPhone().split("-");
        String[] email = member.getMemEmail().split("@");
        String[] address = member.getMemAddress().split("@");
        
        MemberDTO memberDTO = entityToDTO(member, phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return memberDTO;
    }

    @Override
    public void updateMemberInfo(MemberDTO memberDTO) {
        String mergePhoneNum = memberDTO.getMemFirstPhoneNum() + '-' + memberDTO.getMemMiddlePhoneNum() + '-' + memberDTO.getMemLastPhoneNum();
        String mergeEmail = memberDTO.getMemFirstEmail() + '@' + memberDTO.getMemLastEmail();
        String mergeAddress = memberDTO.getMemFirstAddress() + '@' + memberDTO.getMemLastAddress();

        Member member = dtoToEntity(memberDTO, mergePhoneNum, mergeEmail, mergeAddress);
        
        //회원 정보 수정문 안적음
    }
}
