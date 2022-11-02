package com.goodjob.status.repository;

import com.goodjob.status.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * 박채원 22.10.26 수정
 */

public interface StatusRepository extends JpaRepository<Status,Long> {

    Page<Status> getStatusByStatResumeId_ResumeMemId_MemLoginIdOrderByStatApplyDateDesc(String loginId, Pageable pageable);
    Page<Status> getStatusByStatPostId_PostComId_ComLoginIdAndStatPostId_PostIdOrderByStatApplyDateDesc(String loginId, Long postId, Pageable pageable);
    @Transactional
    @Modifying
    @Query("update Status s set s.statPass='서류합격' where s.statId =:statId")
    void updateStatPass(Long statId);
    @Transactional
    @Modifying
    @Query("update Status s set s.statPass='서류불합격' where s.statId =:statId")
    void updateStatUnPass(Long statId);
    @Query("select s from Status s where s.statId =:statId")
    Status findOneApplier(Long statId);
    boolean existsStatusByStatResumeId_ResumeMemId_MemLoginIdAndAndStatShowAndStatPass(String loginId, Short show, String pass);
    int countStatusByStatResumeId_ResumeId(Long resumeId);
    @Transactional
    @Modifying
    @Query("update Status s set s.statShow =:show where s.statResumeId.resumeMemId.memLoginId =:loginId and s.statPass='서류합격'")
    void changeStatShow(Short show, String loginId);
}
