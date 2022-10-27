package com.goodjob.status.repository;

import com.goodjob.member.Member;
import com.goodjob.post.Post;
import com.goodjob.status.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 박채원 22.10.26 수정
 */

public interface StatusRepository extends JpaRepository<Status,Long> {
    int findByStatResumeId_ResumeMemId(String loginId);
    Page<Status> getStatusByStatResumeId_ResumeMemId_MemLoginIdOrderByStatApplyDateDesc(String loginId, Pageable pageable);
}
