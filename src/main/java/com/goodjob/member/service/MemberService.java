package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;

/**
 * 박채원 22.10.02 작성
 */

public interface MemberService {

    ResumeMemberDTO bringMemInfo(String loginId);

    default ResumeMemberDTO entityToDTO(Member member, String firstPhoneNum, String middlePhoneNum, String lastPhoneNum, String firstEmail, String lastEmail, String firstAddress, String lastAddress){
        ResumeMemberDTO resumeMemberDTO = ResumeMemberDTO.builder()
                .memId(member.getMemId())
                .memLoginId(member.getMemLoginId())
                .memName(member.getMemName())
                .memBirthDate(member.getMemBirthDate())
                .memFirstPhoneNum(firstPhoneNum)
                .memMiddlePhoneNum(middlePhoneNum)
                .memLastPhoneNum(lastPhoneNum)
                .memGender(member.getMemGender())
                .memFirstEmail(firstEmail)
                .memLastEmail(lastEmail)
                .memFirstAddress(firstAddress)
                .memLastAddress(lastAddress)
                .build();

        return resumeMemberDTO;
    }

    default Member dtoToEntity(ResumeMemberDTO resumeMemberDTO, String mergePhoneNum, String mergeEmail, String mergeAddress){
        Member member = Member.builder()
                .memId(resumeMemberDTO.getMemId())
                .memLoginId(resumeMemberDTO.getMemLoginId())
                .memName(resumeMemberDTO.getMemName())
                .memBirthDate(resumeMemberDTO.getMemBirthDate())
                .memPhone(mergePhoneNum)
                .memGender(resumeMemberDTO.getMemGender())
                .memEmail(mergeEmail)
                .memAddress(mergeAddress)
                .build();

        return member;
    }
}
