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
        Resume resume = Resume.builder().resumeMemId(member).build();

        resumeRepository.save(resume);
        return resume.getResumeId();  //만약에 오류나면 여기
    }

    @Override
    public void registerResumeMemberInfo(MemberDTO memberDTO) {
        log.info("=========== 이력서 인적사항 수정 ===========");
        String mergePhoneNum = memberDTO.getMemFirstPhoneNum() + '-' + memberDTO.getMemMiddlePhoneNum() + '-' + memberDTO.getMemLastPhoneNum();
        String mergeAddress = memberDTO.getMemFirstAddress() + '@' + memberDTO.getMemLastAddress();
        String mergeEmail = memberDTO.getMemFirstEmail() + '@' + memberDTO.getMemLastEmail();

        Resume resume = dtoToEntity(memberDTO, mergePhoneNum, mergeAddress, mergeEmail);

        resumeRepository.save(resume);
    }


}
