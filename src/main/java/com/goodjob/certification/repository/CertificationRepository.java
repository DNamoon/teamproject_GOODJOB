package com.goodjob.certification.repository;

import com.goodjob.certification.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * 박채원 22.10.03 작성
 * 박채원 22.11.06 수정
 */

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    int countCertificationByResume_ResumeId(Long resumeId);
    List<Certification> findCertificationByResume_ResumeId(Long resumeId);

    @Transactional
    @Modifying
    @Query("update Certification c set c.certificateName.certiName =:certiName, c.certiGetDate =:getDate, c.certiScore =:score where c.certiId =:certiId")
    void updateCertiInfo(String certiName, Date getDate, String score, Long certiId);
}
