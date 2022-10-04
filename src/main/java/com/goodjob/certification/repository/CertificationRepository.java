package com.goodjob.certification.repository;

import com.goodjob.certification.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 박채원 22.10.03 작성
 */

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("select c.resume.resumeId from Certification c where c.resume.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);

}
