package com.goodjob.education.service;

import com.goodjob.education.Education;
import com.goodjob.education.MajorName;
import com.goodjob.education.SchoolName;
import com.goodjob.education.dto.EducationDTO;
import com.goodjob.education.repository.EducationRepository;
import com.goodjob.education.repository.MajorNameRepository;
import com.goodjob.education.repository.SchoolNameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final MajorNameRepository majorNameRepository;
    private final SchoolNameRepository schoolNameRepository;

    @Override
    public void registerSchoolInfo(EducationDTO educationDTO) {
        String mergeCredit = educationDTO.getEduGetCredit() + educationDTO.getEduTotalCredit();
        Education education = dtoToEntity(educationDTO, mergeCredit);

        if(educationRepository.findEducationByResume_ResumeId(educationDTO.getResumeId()) == null){
            log.info("=========== 이력서 학적사항 등록 ===========");
            educationRepository.save(education);
        }else{
            log.info("=========== 이력서 학적사항 수정 ===========");
            educationRepository.updateSchoolInfo(education.getSchoolName().getSchName(),education.getEduGraduationDate(),education.getMajorName().getMajName(),education.getEduCredit(),education.getResume().getResumeId());
        }
    }

    @Override
    public void temporalSchoolInfo(Long resumeId) {
        EducationDTO educationDTO = EducationDTO.builder().schoolName("응답안함").majorName("응답안함").eduGetCredit("0").eduTotalCredit("/4.5").resumeId(resumeId).build();
        registerSchoolInfo(educationDTO);
    }

    @Override
    public EducationDTO bringSchoolInfo(Long resumeId) {
        Education education = educationRepository.findEducationByResume_ResumeId(resumeId);
        String[] credit = education.getEduCredit().split("/");
        EducationDTO educationDTO = entityToDTO(education, credit[0], credit[1]);
        return educationDTO;
    }
    
    @Override
    public List<SchoolName> findSchoolName(String keyword) {
        return schoolNameRepository.findSchoolNameBySchNameContainingAndSchNameNotContaining(keyword, "응답안함");
    }

    @Override
    public List<MajorName> findMajorName(String keyword) {
        return majorNameRepository.findMajorNameByMajNameContainingAndMajNameNotContaining(keyword, "응답안함");
    }
}
