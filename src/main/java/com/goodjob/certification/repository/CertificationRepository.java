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
 */

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("select c.resume.resumeId from Certification c where c.resume.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);

    @Query("select count(c) from Certification c where c.resume.resumeId =:resumeId")
    int countCertiByResumeId(Long resumeId);

    @Query("select c from Certification c where c.resume.resumeId =:resumeId")
    List<Certification> findCertiInfoByResumeId(Long resumeId);

    @Transactional
    @Modifying
    @Query("update Certification c set c.certificateName.certiName =:certiName, c.certiGetDate =:getDate, c.certiScore =:score where c.resume.resumeId =:resumeId")
    void updateCertiInfo(String certiName, Date getDate, String score, Long resumeId);

}
