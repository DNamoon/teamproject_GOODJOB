package com.goodjob.member;

/**
 * 박채원 22.10.02 작성
 */

public interface MemberService {

    MemberDTO bringMemInfo(String loginId);
    void updateMemberInfo(MemberDTO memberDTO);

    default MemberDTO entityToDTO(Member member, String firstPhoneNum, String middlePhoneNum, String lastPhoneNum, String firstEmail, String lastEmail, String firstAddress, String lastAddress){
        MemberDTO memberDTO = MemberDTO.builder()
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

        return memberDTO;
    }

    default Member dtoToEntity(MemberDTO memberDTO, String mergePhoneNum, String mergeEmail, String mergeAddress){
        Member member = Member.builder()
                .memId(memberDTO.getMemId())
                .memLoginId(memberDTO.getMemLoginId())
                .memName(memberDTO.getMemName())
                .memBirthDate(memberDTO.getMemBirthDate())
                .memPhone(mergePhoneNum)
                .memGender(memberDTO.getMemGender())
                .memEmail(mergeEmail)
                .memAddress(mergeAddress)
                .build();

        return member;
    }
}
