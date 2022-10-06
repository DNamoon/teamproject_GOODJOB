package com.goodjob.certification.service;

import com.goodjob.certification.repository.CertificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CertificationServiceImplTest {

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
}