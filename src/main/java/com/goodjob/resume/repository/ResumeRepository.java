package com.goodjob.resume.repository;

import com.goodjob.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 박채원 22.10.02 작성
 */

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select r from Resume r where r.resumeId =:resumeId")
    Resume findResumeInfoByResumeId(Long resumeId);

    @Query("select r.resumeId from Resume r where r.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);

    @Transactional
    @Modifying
    @Query("update Resume r set r.resumeMemPhone =:memPhoneNum, r.resumeMemEmail =:memEmail, r.resumeMemAddress =:memAddress where r.resumeId =:resumeId")
    void updateMemberInfo(String memPhoneNum, String memEmail, String memAddress, Long resumeId);
}
