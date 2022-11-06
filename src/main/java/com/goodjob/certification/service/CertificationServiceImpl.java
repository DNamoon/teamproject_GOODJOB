package com.goodjob.certification.service;

import com.goodjob.certification.CertificateName;
import com.goodjob.certification.Certification;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.repository.CertificationNameRepository;
import com.goodjob.certification.repository.CertificationRepository;
import com.goodjob.resume.Resume;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void registerCertiInfo(List<CertificationDTO> list) {
        if (certificationRepository.countCertificationByResume_ResumeId(list.get(0).getResumeId()) == 0){
            for (int i = 0; i < list.size(); i++) {
                Certification certification = dtoToEntity(list.get(i));
                log.info("=========== 이력서 자격증항목 등록 ===========");
                certificationRepository.save(certification);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                Certification certification = dtoToEntity(list.get(i));
                log.info("=========== 이력서 자격증항목 수정 ===========");
                certificationRepository.updateCertiInfo(certification.getCertificateName().getCertiName(), certification.getCertiGetDate(), certification.getCertiScore(), certification.getCertiId());
            }
        }
    }

    @Override
    public int existOrNotResumeId(Long resumeId) {
        if(certificationRepository.countCertificationByResume_ResumeId(resumeId) == 0){
            return 0;
        }
        return 1;
    }

    @Override
    public List<CertificationDTO> bringCertiInfo(Long resumeId) {
        List<Certification> certificationList = certificationRepository.findCertificationByResume_ResumeId(resumeId);
        List<CertificationDTO> certificationDTOList = new ArrayList<CertificationDTO>();
        for(Certification certification : certificationList){
            certificationDTOList.add(entityToDTO(certification));
        }

        return certificationDTOList;
    }

    @Override
    public List<CertificateName> findCertiName(String keyword) {
        return certificationNameRepository.findCertificateNameByCertiNameContaining(keyword);
    }

    @Override
    public Long addNullCertiInfo(Long resumeId) {
        log.info("======= 빈 자격증 정보 추가 ==========");
        Resume resume = Resume.builder().resumeId(resumeId).build();
        CertificateName certificateName = CertificateName.builder().certiName("없음").build();
        Certification certification = Certification.builder()
                .certificateName(certificateName)
                .resume(resume)
                .build();
        return certificationRepository.save(certification).getCertiId();
    }

    @Override
    public void deleteCertiInfo(Long certiId) {
        certificationRepository.deleteById(certiId);
    }
}