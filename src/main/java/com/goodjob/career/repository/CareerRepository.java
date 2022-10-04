package com.goodjob.career.repository;

import com.goodjob.career.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 박채원 22.10.03 작성
 */

public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("select c.resume.resumeId from Career c where c.resume.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);
}
