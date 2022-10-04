package com.goodjob.certification.service;

import com.goodjob.certification.CertificateName;
import com.goodjob.certification.Certification;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.repository.CertificationNameRepository;
import com.goodjob.certification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationNameRepository certificationNameRepository;

    @Override
    public void registerCertiInfo(CertificationDTO certificationDTO) {
        Certification certification = dtoToEntity(certificationDTO);

        certificationRepository.save(certification);
    }

    @Override
    public void existOrNotResumeId(CertificationDTO certificationDTO) {
        if(certificationRepository.findByResumeId(certificationDTO.getResumeId()) == null){
            Certification certification = dtoToEntity(certificationDTO);
            certificationRepository.save(certification);
        }
    }

    @Override
    public List<CertificateName> findCertiName(String keyword) {
        return certificationNameRepository.findCertiName(keyword);
    }
}
