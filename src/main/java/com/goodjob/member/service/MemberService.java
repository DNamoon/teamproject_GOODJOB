package com.goodjob.member.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.memDTO.ResumeMemberDTO;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    default Member dtoToEntity(ResumeMemberDTO resumeMemberDTO, String mergePhoneNum, String mergeEmail, String mergeAddress) {
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

    /**
     * 김도현 22.9.29 작성
     * 10.19 추가
     **/

    //회원정보 db저장
    Member register(Member member);

    int checkId2(String memLoginId);

    //회원가입 시 아이디 중복 여부 확인
    Long countByMemLoginId(String memLoginId);

    //로그인 시 아이디 존재 여부 확인
    Optional<Member> loginIdCheck(String memLoginId);

    // mypage 개인정보 수정
    MemberDTO memInfo(String loginId);

    void updateMemInfo(MemberDTO memberDTO);

    void deleteById(Long memId);


    String checkEmail(String memEmail);

    String updateEmailCheck(String memEmail, HttpSession session);

    void updatePassword(String tmpPw, String memberEmail, String type);

    String getTmpPassword();

    void changePassword(String changePw, Long memId);

    //22.11.01 ho 추가. 아이디 찾기
    String findId(String name, String email);
}
