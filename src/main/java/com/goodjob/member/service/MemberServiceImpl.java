package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.member.service.MemberService;
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
    public ResumeMemberDTO bringMemInfo(String loginId) {
        Member member = memberRepository.findLoginInfo(loginId);
        String[] phoneNum = member.getMemPhone().split("-");
        String[] email = member.getMemEmail().split("@");
        String[] address = member.getMemAddress().split("@");
        
        ResumeMemberDTO resumeMemberDTO = entityToDTO(member, phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return resumeMemberDTO;
    }

    @Override
    public void updateMemberInfo(ResumeMemberDTO resumeMemberDTO) {
        String mergePhoneNum = resumeMemberDTO.getMemFirstPhoneNum() + '-' + resumeMemberDTO.getMemMiddlePhoneNum() + '-' + resumeMemberDTO.getMemLastPhoneNum();
        String mergeEmail = resumeMemberDTO.getMemFirstEmail() + '@' + resumeMemberDTO.getMemLastEmail();
        String mergeAddress = resumeMemberDTO.getMemFirstAddress() + '@' + resumeMemberDTO.getMemLastAddress();

        Member member = dtoToEntity(resumeMemberDTO, mergePhoneNum, mergeEmail, mergeAddress);
        
        //회원 정보 수정문 안적음
    }
}
