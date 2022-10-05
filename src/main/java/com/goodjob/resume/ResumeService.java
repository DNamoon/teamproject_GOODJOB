package com.goodjob.resume;
import com.goodjob.member.Member;
import com.goodjob.member.MemberDTO;

/**
 * 박채원 22.10.03 작성
 */

public interface ResumeService {
    Long registerResume(String loginId);
    void registerResumeMemberInfo(MemberDTO memberDTO);

    default Resume dtoToEntity(MemberDTO memberDTO, String mergePhoneNum, String mergeAddress, String mergeEmail){
        Member member = Member.builder().memId(memberDTO.getMemId()).build();

        Resume resume = Resume.builder()
                .resumeMemAddress(mergeAddress)
                .resumeMemEmail(mergeEmail)
                .resumeMemPhone(mergePhoneNum)
                .resumeMemId(member)
                .build();

        return resume;
    }
}
