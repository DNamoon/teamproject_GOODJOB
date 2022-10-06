package com.goodjob.resume.service;
import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.resume.Resume;
import com.goodjob.resume.dto.ResumeDTO;

/**
 * 박채원 22.10.03 작성
 */

public interface ResumeService {
    Long registerResume(String loginId);
    void updateResumeMemberInfo(ResumeMemberDTO resumeMemberDTO, Long resumeId);
    ResumeDTO bringResumeInfo(Long resumeId);

    default Resume dtoToEntity(ResumeMemberDTO resumeMemberDTO, String mergePhoneNum, String mergeAddress, String mergeEmail){
        Member member = Member.builder().memId(resumeMemberDTO.getMemId()).build();

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
