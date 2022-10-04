package com.goodjob.resume;

import com.goodjob.member.Member;
import com.goodjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;  //여기 resume랑 관련없는 member리포가 들어와도 괜찮은가

    @Override
    public Long registerResume(String loginId) {
        log.info("=========== 새 이력서 생성 ===========");
        Member member = memberRepository.findLoginInfo(loginId);
        Resume resume = Resume.builder().member(member).build();

        resumeRepository.save(resume);
        return resume.getResumeId();  //만약에 오류나면 여기
    }
}
