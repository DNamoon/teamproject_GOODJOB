package com.goodjob.education.service;

import com.goodjob.education.Education;
import com.goodjob.education.MajorName;
import com.goodjob.education.SchoolName;
import com.goodjob.education.dto.EducationDTO;
import com.goodjob.resume.Resume;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface EducationService {

    void registerSchoolInfo(EducationDTO educationDTO);
    void temporalSchoolInfo(Long resumeId);
    EducationDTO bringSchoolInfo(Long resumeId);
    
    //학교찾기에 필요한 메소드
    List<SchoolName> findSchoolName(String keyword);
    
    //전공찾기에 필요한 메소드
    List<MajorName> findMajorName(String keyword);

    default EducationDTO entityToDTO(Education education, String getCredit, String totalCredit){
        EducationDTO educationDTO = EducationDTO.builder()
                .eduId(education.getEduId())
                .eduGraduationDate(education.getEduGraduationDate())
                .eduGetCredit(getCredit)
                .eduTotalCredit(totalCredit)
                .resumeId(education.getResume().getResumeId())
                .schoolName(education.getSchoolName().getSchName())
                .majorName(education.getMajorName().getMajName())
                .build();

        return educationDTO;
    }

    default Education dtoToEntity(EducationDTO educationDTO, String mergeCredit){
        Resume resume = Resume.builder().resumeId(educationDTO.getResumeId()).build();
        SchoolName schoolName = SchoolName.builder().schName(educationDTO.getSchoolName()).build();
        MajorName majorName = MajorName.builder().majName(educationDTO.getMajorName()).build();

        Education education = Education.builder()
                .eduId(educationDTO.getEduId())
                .eduGraduationDate(educationDTO.getEduGraduationDate())
                .eduCredit(mergeCredit)
                .resume(resume)
                .schoolName(schoolName)
                .majorName(majorName)
                .build();

        return education;
    }
}
