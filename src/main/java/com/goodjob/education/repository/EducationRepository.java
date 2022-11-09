package com.goodjob.education.repository;

import com.goodjob.education.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

/**
 * 박채원 22.10.03 작성
 */

public interface EducationRepository extends JpaRepository<Education, Long> {
    Education findEducationByResume_ResumeId(Long resumeId);
    @Transactional
    @Modifying
    @Query("update Education e set e.schoolName.schName =:schoolName, e.eduGraduationDate =:graduDate, e.majorName.majName =:majorName, e.eduCredit =:credit where e.resume.resumeId =:resumeId")
    void updateSchoolInfo(String schoolName, Date graduDate, String majorName, String credit, Long resumeId);
}
