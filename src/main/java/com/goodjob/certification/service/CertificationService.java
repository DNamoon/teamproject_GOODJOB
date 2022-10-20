package com.goodjob.certification.service;

import com.goodjob.certification.CertificateName;
import com.goodjob.certification.Certification;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.resume.Resume;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface CertificationService {

    void registerCertiInfo(List<CertificationDTO> certificationDTO);
    int existOrNotResumeId(Long resumeId);
    List<CertificationDTO> bringCertiInfo(Long resumeId);

    //자격증 검색을 위한 메소드
    List<CertificateName> findCertiName(String keyword);
    Long addNullCertiInfo(Long resumeId);
    void deleteCertiInfo(Long certiId);

    default Certification dtoToEntity(CertificationDTO certificationDTO){
        Resume resume = Resume.builder().resumeId(certificationDTO.getResumeId()).build();
        CertificateName certificateName = CertificateName.builder().certiName(certificationDTO.getCertificateName()).build();

        Certification certification = Certification.builder()
                .certiId(certificationDTO.getCertiId())
                .certiGetDate(certificationDTO.getCertiGetDate())
                .certiScore(certificationDTO.getCertiScore())
                .resume(resume)
                .certificateName(certificateName)
                .build();

        return certification;
    }

    default CertificationDTO entityToDTO(Certification certification){
        CertificationDTO certificationDTO = CertificationDTO.builder()
                .certiId(certification.getCertiId())
                .certiGetDate(certification.getCertiGetDate())
                .certiScore(certification.getCertiScore())
                .resumeId(certification.getResume().getResumeId())
                .certificateName(certification.getCertificateName().getCertiName())
                .build();

        return certificationDTO;
    }
}