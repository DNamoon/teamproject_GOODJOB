package com.goodjob.resume;
import com.goodjob.member.Member;
import com.goodjob.member.MemberDTO;

/**
 * 박채원 22.10.03 작성
 */

public interface ResumeService {
    Long registerResume(String loginId);
    void updateResumeMemberInfo(MemberDTO memberDTO, Long resumeId);
    ResumeDTO bringResumeInfo(Long resumeId);

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

    default ResumeDTO entityToDTO(Resume resume, String firstPhoneNum, String middlePhoneNum, String lastPhoneNum, String firstEmail, String lastEmail, String firstAddress, String lastAddress){
        ResumeDTO resumeDTO = ResumeDTO.builder()
                .memFirstAddress(firstAddress)
                .memLastAddress(lastAddress)
                .memFirstEmail(firstEmail)
                .memLastEmail(lastEmail)
                .memFirstPhoneNum(firstPhoneNum)
                .memMiddlePhoneNum(middlePhoneNum)
                .memLastPhoneNum(lastPhoneNum)
                .build();

        return resumeDTO;
    }
}
