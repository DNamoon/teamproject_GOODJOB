package com.goodjob.resume.service;
import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.resume.Resume;
import com.goodjob.resume.dto.ResumeDTO;
import com.goodjob.resume.dto.ResumeListDTO;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface ResumeService {
    Long registerResume(String loginId);
    void updateResumeMemberInfo(ResumeMemberDTO resumeMemberDTO, Long resumeId);
    ResumeDTO bringResumeInfo(Long resumeId);
    List<ResumeListDTO> getResumeList(String loginId);
    void deleteResume(List<String> resumeId);
    void changeTitle(Long resumeId, String title);

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

    default ResumeListDTO entityToListDTO(Resume resume){
        ResumeListDTO resumeListDTO = ResumeListDTO.builder()
                .resumeId(resume.getResumeId())
                .resumeTitle(resume.getResumeTitle())
                .resumeUpdateDate(resume.getResumeUpdateDate())
                .build();

        return resumeListDTO;
    }

    default Resume listDTOToEntity(ResumeListDTO resumeListDTO){
        Resume resume = Resume.builder()
                .resumeId(resumeListDTO.getResumeId())
                .resumeTitle(resumeListDTO.getResumeTitle())
                .build();

        return resume;
    }
}
