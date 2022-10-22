package com.goodjob.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 김도현 10.20 작성
 * 임시 비밀번호 메일 발송하기
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;
    public void sendMail(String findPwEmail,String tempPw) {

        // 수신 대상을 담을 ArrayList 생성
        ArrayList<String> toUserList = new ArrayList<>();

        // 수신 대상 추가
        toUserList.add(findPwEmail);

        // 수신 대상 개수(수신 대상이 여러명일 수 있으니까)
        int toUserSize = toUserList.size();

        // SimpleMailMessage (단순 텍스트 구성 메일 메시지 생성할 때 이용)
        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        // 수신자 설정
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        // 메일 제목
        simpleMessage.setSubject("goodjob 임시 비밀번호 안내 이메일입니다.");

        // 메일 내용(임시비밀번호 같이 보냄)
        simpleMessage.setText( "안녕하세요. goodjob 임시 비밀번호 안내 메일입니다. "
                +"\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."+"\n"
                + tempPw);

        mailSender.send(simpleMessage);
    }
}
