package com.goodjob.selfIntroduction.repository;

import com.goodjob.selfIntroduction.SelfIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 박채원 22.10.03 작성
 */

public interface SelfIntroductionRepository extends JpaRepository<SelfIntroduction, Long> {
    @Query("select s from SelfIntroduction s where s.resume.resumeId =:resumeId")
    SelfIntroduction findSelfIntroInfoByResumeId(Long resumeId);
    @Query("select s.resume.resumeId from SelfIntroduction s where s.resume.resumeId =:resumeId")
    Long findByResumeId(Long resumeId);

    @Transactional
    @Modifying
    @Query("update SelfIntroduction s set s.selfInterActivity =:interActivity, s.selfLetter =:selfLetter where s.resume.resumeId =:resumeId")
    void updateSelfIntroInfo(String interActivity, String selfLetter, Long resumeId);
}
