package com.goodjob.member.memDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 김도현 22.10.19 작성
 * 임시비밀번호 메일 발송을 위한 dto
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String toAddress; // 받는 이메일 주소
    private String title; // 이메일 제목
    private String message; // 이메일 내용
    private String fromAddress; // 보내는 이메일 주소

}
