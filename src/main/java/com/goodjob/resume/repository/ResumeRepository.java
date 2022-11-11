package com.goodjob.resume.repository;

import com.goodjob.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.02 작성
 */

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Resume findByResumeId(Long resumeId);
    Resume findByResumeMemIdAndAndResumeId(String memId, Long resumeId);
    @Transactional
    @Modifying
    @Query("update Resume r set r.resumeMemPhone =:memPhoneNum, r.resumeMemEmail =:memEmail, r.resumeMemAddress =:memAddress where r.resumeId =:resumeId")
    void updateMemberInfo(String memPhoneNum, String memEmail, String memAddress, Long resumeId);
    List<Resume> getResumeByResumeMemId_MemLoginIdAndDeletedOrderByResumeId(String memId, boolean deleted);
    List<Resume> getResumeByResumeMemId_MemLoginIdAndDeletedAndSubmittedOrderByResumeId(String memId, boolean deleted, boolean submitted);
    @Transactional
    void deleteByResumeId(Long resumeId);
    @Transactional
    @Modifying
    @Query("update Resume r set r.resumeTitle =:title where r.resumeId =:resumeId")
    void changeTitle(String title, Long resumeId);
    @Transactional
    @Modifying
    @Query("update Resume r set r.deleted = true where r.resumeId =:resumeId")
    void setDelete(Long resumeId);
    @Transactional
    @Modifying
    @Query("update Resume r set r.resumeMemId = null where r.resumeMemId.memId =:memId")
    void setMemberIdNull(Long memId);
    int countResumeByResumeMemId_MemLoginIdAndDeleted(String loginId, boolean deleted);
    @Transactional
    @Modifying
    @Query("update Resume r set r.submitted = true where r.resumeId =:resumeId")
    void setSubmitted(Long resumeId);
}
