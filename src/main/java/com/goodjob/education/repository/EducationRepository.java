package com.goodjob.education.repository;

import com.goodjob.education.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 박채원 22.10.03 작성
 */

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query("select e from Education e where e.resume.resumeId =:resumeId")
    Education findSchoolInfoByResumeId(Long resumeId);
}
