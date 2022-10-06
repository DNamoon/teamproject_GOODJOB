package com.goodjob.member.memDTO;

import com.goodjob.member.Member;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class MemberDTO {
    private Long memId;
    private String memLoginId;
    private String memPw;
    private String memPhone;
    private String memEmail;
    private String memName;
    private Date memBirthDate;
    private String memAddress;
    private String detailAddress;
    private String memGender;
    private String memTerms;

    public Member toEntity() {
        Member member = Member.builder()
                .memId(memId)
                .memLoginId(memLoginId)
                .memPw(memPw)
                .memName(memName)
                .memPhone(memPhone)
                .memEmail(memEmail)
                .memBirthDate(memBirthDate)
                .memAddress(memAddress +"@"+ detailAddress)
                .memGender(memGender)
                .memTerms(memTerms)
                .build();
        return member;
    }

}
