package com.goodjob.certification.service;

import com.goodjob.certification.CertificateName;
import com.goodjob.certification.Certification;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.repository.CertificationNameRepository;
import com.goodjob.certification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationNameRepository certificationNameRepository;

    @Override
    public void registerCertiInfo(CertificationDTO certificationDTO) {
            Certification certification = dtoToEntity(certificationDTO);

            if(certificationRepository.findByResumeId(certificationDTO.getResumeId()) == null){
                log.info("=========== 이력서 자격증항목 등록 ===========");
                certificationRepository.save(certification);
            }else{
                log.info("=========== 이력서 자격증항목 수정 ===========");
                certificationRepository.updateCertiInfo(certification.getCertificateName().getCertiName(),certification.getCertiGetDate(),certification.getCertiScore(),certification.getResume().getResumeId());
            }
    }

    @Override
    public int existOrNotResumeId(Long resumeId) {
        if(certificationRepository.findByResumeId(resumeId) == null){
            return 0;
        }
        return 1;
    }

    @Override
    public CertificationDTO bringCertiInfo(Long resumeId) {
        Certification certification = certificationRepository.findCertiInfoByResumeId(resumeId);
        CertificationDTO certificationDTO = entityToDTO(certification);
        return certificationDTO;
    }

    @Override
    public List<CertificateName> findCertiName(String keyword) {
        return certificationNameRepository.findCertiName(keyword);
    }
}
