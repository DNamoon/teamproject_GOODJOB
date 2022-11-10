package com.goodjob.member.service;

import com.goodjob.status.dto.SendMailDTO;
import com.goodjob.status.dto.SendMailToIntervieweeDTO;


/**
 * 김도현 22.10.19 작성
 * 박채원 22.11.01 추가 - 서류지원자들에게 결과 메일 보내는 메소드
 * 박채원 22.11.05 추가 - 서류합격자들에게 면접 안내 메일 보내는 메소드
 */

public interface MailService {
    void sendMail(String findPwEmail,String tempPw);
    void sendMailToResumeApplier(SendMailDTO sendMailDTO);
    void sendMailAboutInterviewInfo(SendMailToIntervieweeDTO sendMailToIntervieweeDTO);
}
