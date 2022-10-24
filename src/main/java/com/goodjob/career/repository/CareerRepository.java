package com.goodjob.career.repository;

import com.goodjob.career.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface CareerRepository extends JpaRepository<Career, Long> {
    @Query("select c from Career c where c.resume.resumeId =:resumeId")
    List<Career> findCareerInfoByResumeId(Long resumeId);

    @Query("select c.resume.resumeId from Career c where c.resume.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);

    @Query("select count(c) from Career c where c.resume.resumeId =:resumeId")
    int countCareerByResumeId(Long resumeId);

    @Transactional
    @Modifying
    @Query("update Career c set c.careerCompanyName =:companyName, c.careerJoinedDate =:joinDate, c.careerRetireDate =:retireDate, c.careerTask =:task where c.careerId =:careerId")
    void updateCareerInfo(String companyName, Date joinDate, Date retireDate, String task, Long careerId);
}
