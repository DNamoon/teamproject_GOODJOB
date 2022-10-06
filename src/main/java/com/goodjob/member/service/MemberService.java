package com.goodjob.member.service;

import com.goodjob.member.Member;

/**
 * 김도현 22.9.29 작성
 **/
public interface MemberService {
    //회원정보 db저장
    Member register(Member member);

    //회원가입 시 아이디 중복 여부 확인
    Long countByMemLoginId(String memLoginId);


    Member loginIdCheck(String memLoginId);
}
