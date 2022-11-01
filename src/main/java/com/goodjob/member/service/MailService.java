package com.goodjob.member.service;

import com.goodjob.status.dto.SendMailDTO;

import java.util.Optional;

/**
 * 김도현 22.10.19 작성
 * 박채원 22.11.01 추가 - 서류지원자들에게 결과 메일 보내는 메소드
 */

public interface MailService {
    void sendMail(String findPwEmail,String tempPw);
    
    void sendMailToResumeApplier(SendMailDTO sendMailDTO);
}
