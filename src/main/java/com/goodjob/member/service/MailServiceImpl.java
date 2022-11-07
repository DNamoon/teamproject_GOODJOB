package com.goodjob.member.service;

import com.goodjob.status.dto.SendMailDTO;
import com.goodjob.status.dto.SendMailToIntervieweeDTO;
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

    @Override
    public void sendMailToResumeApplier(SendMailDTO sendMailDTO) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(sendMailDTO.getApplierEmail());

        if(sendMailDTO.getStatPass().equals("서류합격")){
            simpleMessage.setSubject("["+ sendMailDTO.getCompanyName() +"] "+ sendMailDTO.getPostName() +" 직무 서류 전형 결과 안내");

            simpleMessage.setText("안녕하십니까? "+ sendMailDTO.getCompanyName() +"입니다.\n " +
                    "우리 회사 서류 전형 지원에 감사드립니다.\n\n" +
                    sendMailDTO.getApplierName() +" 님은 이번 서류전형에 합격하였음을 알려드립니다.\n\n" +
                    "이후 전형에 대한 안내는 빠른 시일 내에 메일 드리겠습니다.\n" +
                    "건강 유의하시고, 빛나는 하루 되시기 바랍니다. 감사합니다.\n\n" +
                    sendMailDTO.getCompanyName() +" 채용담당자 드림.");

        }else if(sendMailDTO.getStatPass().equals("서류불합격")){
            simpleMessage.setSubject("["+ sendMailDTO.getCompanyName() +"] "+ sendMailDTO.getPostName() +" 직무 서류 전형 결과 안내");

            simpleMessage.setText("안녕하십니까? "+ sendMailDTO.getCompanyName() +"입니다.\n " +
                    "우리 회사 서류 전형 지원에 감사드립니다.\n\n" +
                    sendMailDTO.getApplierName() +" 님은 안타깝게도 이번 서류전형에 합격을 전해드리지 못하게 되었습니다.\n\n" +
                    "비록 이번 전형을 통해 "+ sendMailDTO.getApplierName() +"님과 좋은 인연으로 함께 하지는 못하지만,\n" +
                    "스스로 꿈꾸는 인생은 반드시 실현되리라 믿으며 진심 어린 응원의 마음을\n" +
                    "전해드립니다.\n\n" +
                    "건강 유의하시고, 빛나는 하루 되시기 바랍니다. 감사합니다.\n\n" +
                    sendMailDTO.getCompanyName() +" 채용담당자 드림.");
        }else if(sendMailDTO.getStatPass().equals("면접불합격")){
            simpleMessage.setSubject("["+ sendMailDTO.getCompanyName() +"] "+ sendMailDTO.getPostName() +" 직무 면접 전형 결과 안내");

            simpleMessage.setText("안녕하십니까? "+ sendMailDTO.getCompanyName() +"입니다.\n " +
                    "우리 회사 면접 전형 참여에 감사드립니다.\n\n" +
                    sendMailDTO.getApplierName() +" 님은 안타깝게도 이번 면접전형에 합격을 전해드리지 못하게 되었습니다.\n\n" +
                    "비록 이번 전형을 통해 "+ sendMailDTO.getApplierName() +"님과 좋은 인연으로 함께 하지는 못하지만,\n" +
                    "스스로 꿈꾸는 인생은 반드시 실현되리라 믿으며 진심 어린 응원의 마음을\n" +
                    "전해드립니다.\n\n" +
                    "건강 유의하시고, 빛나는 하루 되시기 바랍니다. 감사합니다.\n\n" +
                    sendMailDTO.getCompanyName() +" 채용담당자 드림.");
        }else if(sendMailDTO.getStatPass().equals("최종합격")){
            simpleMessage.setSubject("["+ sendMailDTO.getCompanyName() +"] "+ sendMailDTO.getPostName() +" 직무 면접 전형 결과 안내");

            simpleMessage.setText("안녕하십니까? "+ sendMailDTO.getCompanyName() +"입니다.\n " +
                    "우리 회사 면접 전형 참여에 감사드립니다.\n\n" +
                    sendMailDTO.getApplierName() +" 님은 " + sendMailDTO.getPostName() + " 직무에 최종 합격하였음을 알려드립니다.\n" +
                    "진심으로 축하 드립니다!\n\n" +
                    "안내에 따라 입사를 준비해 주시기 바랍니다.\n" +
                    "곧 신입사원으로 만나 뵙기를 기쁜 마음으로 기다리고 있겠습니다.\n\n" +
                    sendMailDTO.getCompanyName() +" 채용담당자 드림.");
        }

        mailSender.send(simpleMessage);
    }

    @Override
    public void sendMailAboutInterviewInfo(SendMailToIntervieweeDTO sendMailToIntervieweeDTO) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(sendMailToIntervieweeDTO.getApplierEmail());

        simpleMessage.setSubject("["+ sendMailToIntervieweeDTO.getCompanyName() +"] "+ sendMailToIntervieweeDTO.getPostName() +" 직무 면접 일정 안내");

        simpleMessage.setText("안녕하십니까? "+ sendMailToIntervieweeDTO.getCompanyName() +"입니다.\n " +
                "2차 전형인 면접 장소 및 날짜를 안내해드리고자 합니다.\n\n" +
                sendMailToIntervieweeDTO.getApplierName() +" 님의 면접 일정은 다음과 같습니다.\n" +
                "면접 날짜 : " + sendMailToIntervieweeDTO.getInterviewDate() + "\n" +
                "면접 장소 : " + sendMailToIntervieweeDTO.getInterviewPlace() + "\n\n" +
                "안내된 면접 날짜와 시간을 확인하시고 시간에 맞춰 면접 장소에서 대기해 주시기 바랍니다.\n" +
                "건강 유의하시고, 빛나는 하루 되시기 바랍니다. 감사합니다.\n\n" +
                sendMailToIntervieweeDTO.getCompanyName() +" 채용담당자 드림.");

        mailSender.send(simpleMessage);
    }
}
