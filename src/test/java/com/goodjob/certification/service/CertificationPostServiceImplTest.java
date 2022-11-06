package com.goodjob.certification.service;

import com.goodjob.certification.CertificateName;
import com.goodjob.certification.Certification;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.repository.CertificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CertificationPostServiceImplTest implements CertificationService{

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    void registerCertiInfo() {
        String certiName = "TOEIC SPEAKING";
        Date getDate = Date.valueOf("2022-11-11");
        String score = "880";
        Long resumeId = 36L;
        certificationRepository.updateCertiInfo(certiName, getDate, score, resumeId);
    }

    @Test
    void showPrint(){
        Long resumeId = 226L;
        List<Certification> certificationList = certificationRepository.findCertificationByResume_ResumeId(resumeId);
        System.out.println("====ENTITY====" + certificationList);
        List<CertificationDTO> certificationDTOList = new ArrayList<>();
        for(Certification certification : certificationList){
            System.out.println(certification);
            certificationDTOList.add(entityToDTO(certification));
        }
        System.out.println("====DTO====" + certificationDTOList);
    }

    @Override
    public void registerCertiInfo(List<CertificationDTO> certificationDTO) {

    }

    @Override
    public int existOrNotResumeId(Long resumeId) {
        return 0;
    }

    @Override
    public List<CertificationDTO> bringCertiInfo(Long resumeId) {
        return null;
    }

    @Override
    public List<CertificateName> findCertiName(String keyword) {
        return null;
    }

    @Override
    public Long addNullCertiInfo(Long resumeId) {
        return null;
    }

    @Override
    public void deleteCertiInfo(Long certiId) {

    }
}