package com.goodjob.status.repository;

import com.goodjob.member.Member;
import com.goodjob.post.Post;
import com.goodjob.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 박채원 22.10.26 수정
 */

public interface StatusRepository extends JpaRepository<Status,Long> {
    List<Status> getStatusByStatResumeId_ResumeMemId_MemLoginIdOOrderByStatApplyDateDesc(String loginId);
}
