package com.goodjob.resume;

import com.goodjob.member.Member;
import com.goodjob.member.MemberDTO;
import com.goodjob.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long registerResume(String loginId) {
        log.info("=========== 새 이력서 생성 ===========");
        Member member = memberRepository.findLoginInfo(loginId);
        Resume resume = Resume.builder()
                .resumeMemId(member).resumeMemAddress(member.getMemAddress())
                .resumeMemEmail(member.getMemEmail()).resumeMemPhone(member.getMemPhone())
                .build();

        resumeRepository.save(resume);
        return resume.getResumeId();
    }

    @Override
    public void updateResumeMemberInfo(MemberDTO memberDTO, Long resumeId) {
        String mergePhoneNum = memberDTO.getMemFirstPhoneNum() + '-' + memberDTO.getMemMiddlePhoneNum() + '-' + memberDTO.getMemLastPhoneNum();
        String mergeAddress = memberDTO.getMemFirstAddress() + '@' + memberDTO.getMemLastAddress();
        String mergeEmail = memberDTO.getMemFirstEmail() + '@' + memberDTO.getMemLastEmail();

        Resume resume = dtoToEntity(memberDTO, mergePhoneNum, mergeAddress, mergeEmail);

        log.info("=========== 이력서 인적사항 수정 ===========");
        resumeRepository.updateMemberInfo(mergePhoneNum, mergeEmail, mergeAddress, resumeId);
    }

    @Override
    public ResumeDTO bringResumeInfo(Long resumeId) {
        Resume resume = resumeRepository.findResumeInfoByResumeId(resumeId);
        String[] phoneNum = resume.getResumeMemPhone().split("-");
        String[] email = resume.getResumeMemEmail().split("@");
        String[] address = resume.getResumeMemAddress().split("@");

        ResumeDTO resumeDTO = entityToDTO(resume,phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return resumeDTO;
    }
}
