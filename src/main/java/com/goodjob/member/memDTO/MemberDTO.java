/**
 * ho - 22.10.17 로그인 폼 통일위해
 * loginId,pw 필드 통일
 * 18,19,33,34라인 변경 (memLoginId->loginId , memPw->pw)
 */
package com.goodjob.member.memDTO;

import com.goodjob.member.Member;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class MemberDTO {
    private Long memId;
    private String loginId;
    private String pw;
    private String memPhone;
    private String memEmail1;
    private String memEmail2;
    private String memName;
    private Date memBirthDate;
    private String memAddress;
    private String detailAddress;
    private String memGender;
    private String memTerms;

    public Member toEntity() {
        Member member = Member.builder()
                .memId(memId)
                .memLoginId(loginId)
                .memPw(pw)
                .memName(memName)
                .memPhone(memPhone)
                .memEmail(memEmail1+"@"+memEmail2)
                .memBirthDate(memBirthDate)
                .memAddress(memAddress +"@"+ detailAddress)
                .memGender(memGender)
                .memTerms(memTerms)
                .build();
        return member;
    }

}
